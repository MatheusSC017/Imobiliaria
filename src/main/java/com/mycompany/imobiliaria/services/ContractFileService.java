/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.imobiliaria.services;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import java.util.function.Consumer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ContractFileService {

    private static final String CONTRACTS_DIR = "internal_storage/contracts";

    public ContractFileService() {
        File storageDir = new File(CONTRACTS_DIR);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
    }

    public static String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filePath.substring(dotIndex + 1);
    }

    public String saveContractFile(Component parentComponent, String contractId) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o arquivo do contrato");
        int result = fileChooser.showOpenDialog(parentComponent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = getFileExtension(selectedFile.getName());
            if (extension.isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, "Arquivo inválido: sem extensão.", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            File destFile = new File(CONTRACTS_DIR, contractId + "." + extension);

            try (InputStream in = new FileInputStream(selectedFile);
                 OutputStream out = new FileOutputStream(destFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                JOptionPane.showMessageDialog(parentComponent, "Contrato salvo com sucesso!");
                return destFile.getAbsolutePath();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Erro ao salvar o contrato: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }

    private static class PdfLoaderWorker extends SwingWorker<Image, Void> {
        private final String contractPath;
        private final Consumer<Image> onComplete;
        private final Consumer<Exception> onError;

        public PdfLoaderWorker(String contractPath, Consumer<Image> onComplete, Consumer<Exception> onError) {
            this.contractPath = contractPath;
            this.onComplete = onComplete;
            this.onError = onError;
        }

        @Override
        protected Image doInBackground() throws Exception {
            File file = new File(contractPath);
            if (!file.exists()) {
                throw new IOException("Arquivo PDF não encontrado em: " + contractPath);
            }
            PDDocument document = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(document);

            int numPages = document.getNumberOfPages();
            int dpi = 150; // Adjust DPI as needed for quality/performance
            List<BufferedImage> contractPages = new ArrayList<>();
            int totalHeight = 0;
            int maxWidth = 0;
            int separatorHeight = 5;

            for (int pageIndex = 0; pageIndex < numPages; pageIndex++) {
                BufferedImage pageImage = renderer.renderImageWithDPI(pageIndex, dpi);
                contractPages.add(pageImage);
                totalHeight += pageImage.getHeight();
                if (pageIndex != numPages - 1) {
                    totalHeight += separatorHeight;
                }
                maxWidth = Math.max(maxWidth, pageImage.getWidth());
            }

            if (numPages > 0 && (maxWidth == 0 || totalHeight == 0 && contractPages.stream().allMatch(p -> p.getWidth() == 0 || p.getHeight() == 0))) {
                 System.err.println("Renderização do PDF resultou em imagem vazia para " + contractPath);
                 document.close();
                 throw new IOException("PDF rendering resulted in an empty image.");
            }
            if (numPages == 0) {
                document.close();
                return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }


            BufferedImage fullContractImage = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = fullContractImage.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, maxWidth, totalHeight);

            int currentY = 0;
            for (BufferedImage page : contractPages) {
                g2d.drawImage(page, 0, currentY, null);
                currentY += page.getHeight();
                if (currentY < totalHeight && (currentY - page.getHeight() + separatorHeight) < totalHeight ) {
                    g2d.setColor(Color.BLACK); 
                    g2d.fillRect(0, currentY, maxWidth, separatorHeight);
                    currentY += separatorHeight;
                }
            }
            g2d.dispose();
            document.close();
            return fullContractImage;
        }

        @Override
        protected void done() {
            try {
                Image result = get();
                onComplete.accept(result);
            } catch (Exception e) {
                onError.accept(e);
            }
        }
    }

    public void loadContractImage(String contractPath, Consumer<Image> onComplete, Consumer<Exception> onError) {
        Image defaultErrorImage = new ImageIcon(getClass().getResource("/static/icons/documentNotSupported.png")).getImage();

        if (contractPath == null || contractPath.trim().isEmpty()) {
            onComplete.accept(defaultErrorImage);
            return;
        }

        String extension = getFileExtension(contractPath).toLowerCase();

        try {
            switch (extension) {
                case "jpg":
                case "jpeg":
                case "png":
                    Image loadedImage = new ImageIcon(contractPath).getImage();
                    System.out.println("Image found");
                    if (loadedImage.getWidth(null) == -1) {
                        throw new IOException("Falha ao carregar imagem: " + contractPath);
                    }
                    onComplete.accept(loadedImage);
                    break;
                case "pdf":
                    new PdfLoaderWorker(contractPath, onComplete, onError).execute();
                    break;
                default:
                    onComplete.accept(defaultErrorImage);
                    break;
            }
        } catch (Exception ex) {
            onError.accept(ex);
        }
    }

    public boolean downloadContractFile(Component parentComponent, String currentContractPath) {
        if (currentContractPath == null || currentContractPath.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentComponent, "Nenhum contrato para baixar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        File sourceFile = new File(currentContractPath);
        if (!sourceFile.exists()) {
            JOptionPane.showMessageDialog(parentComponent, "Arquivo do contrato não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar contrato como...");
        fileChooser.setSelectedFile(new File(sourceFile.getName()));

        int userSelection = fileChooser.showSaveDialog(parentComponent);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File destinationFile = fileChooser.getSelectedFile();
            try {
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(parentComponent, "Contrato baixado com sucesso!");
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Erro ao baixar o contrato: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false; // User cancelled
    }
}
