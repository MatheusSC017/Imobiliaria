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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author user
 */
public class FileService {
    private String storageDir;

    public FileService(String storageDir) {
        this.storageDir = storageDir;
        File storageDirFile = new File(storageDir);
        if (!storageDirFile.exists()) {
            storageDirFile.mkdirs();
        }
    }

    public static String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filePath.substring(dotIndex + 1);
    }

    public String saveFile(Component parentComponent, String fileId) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o arquivo");
        int result = fileChooser.showOpenDialog(parentComponent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = getFileExtension(selectedFile.getName());
            if (extension.isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, "Arquivo inválido: sem extensão.", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            File destFile = new File(this.storageDir, fileId + "." + extension);

            try (InputStream in = new FileInputStream(selectedFile);
                 OutputStream out = new FileOutputStream(destFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                JOptionPane.showMessageDialog(parentComponent, "Arquivo salvo com sucesso!");
                return destFile.getAbsolutePath();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Erro ao salvar o arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }
    
    public Image pdfLoader(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IOException("Arquivo PDF não encontrado em: " + filePath);
            }
            
            PDDocument document = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(document);

            int numPages = document.getNumberOfPages();
            int dpi = 150;
            List<BufferedImage> filePages = new ArrayList<>();
            int totalHeight = 0;
            int maxWidth = 0;
            int separatorHeight = 5;

            for (int pageIndex = 0; pageIndex < numPages; pageIndex++) {
                BufferedImage pageImage = renderer.renderImageWithDPI(pageIndex, dpi);
                filePages.add(pageImage);
                totalHeight += pageImage.getHeight();
                if (pageIndex != numPages - 1) {
                    totalHeight += separatorHeight;
                }
                maxWidth = Math.max(maxWidth, pageImage.getWidth());
            }
            
            if (numPages > 0 && (maxWidth == 0 || totalHeight == 0 && filePages.stream().allMatch(p -> p.getWidth() == 0 || p.getHeight() == 0))) {
                 System.err.println("Renderização do PDF resultou em imagem vazia para " + filePath);
                 document.close();
                 throw new IOException("PDF rendering resulted in an empty image.");
            }
            if (numPages == 0) {
                document.close();
                return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }

            BufferedImage fullDocumentImage = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = fullDocumentImage.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, maxWidth, totalHeight);

            int currentY = 0;
            for (BufferedImage page : filePages) {
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
            return fullDocumentImage;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Image loadImage(String filePath) {
        Image defaultErrorImage = new ImageIcon(getClass().getResource("/static/icons/documentNotSupported.png")).getImage();

        if (filePath == null || filePath.trim().isEmpty()) {
            return defaultErrorImage;
        }

        String extension = getFileExtension(filePath).toLowerCase();
        
        Image loadedImage = defaultErrorImage;
        try {
            switch (extension) {
                case "jpg":
                case "jpeg":
                case "png":
                    loadedImage = new ImageIcon(filePath).getImage();
                    if (loadedImage.getWidth(null) == -1) {
                        throw new IOException("Falha ao carregar imagem: " + filePath);
                    }
                    break;
                case "pdf":
                    loadedImage = pdfLoader(filePath);
                    break;
            }
        } catch (Exception ex) {
            return defaultErrorImage;
        }
        return loadedImage;
    }

    public boolean downloadFile(Component parentComponent, String currentFilePath) {
        if (currentFilePath == null || currentFilePath.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentComponent, "Nenhum arquivo para baixar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        File sourceFile = new File(currentFilePath);
        if (!sourceFile.exists()) {
            JOptionPane.showMessageDialog(parentComponent, "Arquivo não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar arquivo como...");
        fileChooser.setSelectedFile(new File(sourceFile.getName()));

        int userSelection = fileChooser.showSaveDialog(parentComponent);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File destinationFile = fileChooser.getSelectedFile();
            try {
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(parentComponent, "Arquivo baixado com sucesso!");
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Erro ao baixar o arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }
}
