package com.monolitica.crud.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import com.monolitica.crud.dto.OrderDetailDTO;
import com.monolitica.crud.dto.PrintDTO;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PDFGenerator {

    //protected Map<String, Object>  saleInfo;
    protected final String SALON = "salon";
    protected final String COCINA = "cocina";   
    protected final String BAR = "bar";

    /* public PDFGenerator( Map<String, Object> saleInfo) throws IOException {
        this.saleInfo = saleInfo;
    } */
    public PDFGenerator() throws IOException {
    }

    public void createPDFKitchen(PrintDTO saleInfo, String printerName) throws IOException, PrinterException {
        String place = saleInfo.getPlace();
        List<OrderDetailDTO> orders;
        if(place.equals(SALON)){
            List<OrderDetailDTO> orders2 =   new ArrayList<>(saleInfo.getOrderDetails());
            Map<Integer, OrderDetailDTO> detailsMap = new HashMap<>();

                for (OrderDetailDTO value : orders2) {
                    if (!detailsMap.containsKey(value.getProducto_id())) {
                        detailsMap.put(value.getProducto_id(), value);
                    } else {
                        OrderDetailDTO existingDetail = detailsMap.get(value.getProducto_id());
                        existingDetail.setCantidad(existingDetail.getCantidad() + value.getCantidad());
                    }
                }

            orders = List.copyOf(detailsMap.values());
        }else{
            orders = saleInfo.getOrderDetails();
        }

        printPDF(saleInfo, printerName, orders);
    }

    protected void printPDF(PrintDTO saleInfo, String printerName, List<OrderDetailDTO> orders ) throws PrinterException, IOException {
        PrintService printService = findPrintService(printerName);
   

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintService(printService);
        // Crear una instancia de Paper para establecer el tamaño de la página
        Paper paper = new Paper();

        
        paper.setSize(200, 500); // Establecer el tamaño de la página en puntos (1 punto = 1/72 de pulgada)

        // Establecer el tamaño de la imagen imprimible dentro de la página
        double scale = 1; // Escala personalizada (por ejemplo, 1.5 significa aumentar el tamaño en un 50%)
        double imageableWidth = paper.getWidth() * scale;
        double imageableHeight = paper.getHeight() * scale;
        double imageableX = (paper.getWidth() - imageableWidth) / 2;
        double imageableY = (paper.getHeight() - imageableHeight) / 2;
        paper.setImageableArea(imageableX, imageableY, imageableWidth, imageableHeight);

        // Establecer el Paper en la PageFormat
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
    
        
        /* Version actuliazada x */
        String place = saleInfo.getPlace();
        printerJob.setPrintable((graphics, pf, pageIndex) -> {
        if (pageIndex == 0 ) { // Solo dibujar en la primera página (pageIndex = 0)
            Graphics2D g2d = (Graphics2D) graphics;
            // Obtener las dimensiones de la página escalada
            double pageWidth = pf.getImageableWidth();

            // Establecer la fuente y el tamaño de la letra para el texto "Cliente: customer"
            String cliente = saleInfo.getCliente();
            Font font12 = new Font("Arial", Font.PLAIN, 12);
            g2d.setFont(font12);
            String customerText = "Cliente: " + cliente;
            g2d.drawString(customerText, 0, font12.getSize());

            // Establecer la fuente y el tamaño de la letra para el texto "username: {user}   fecha: {fechaActual}"
            Font font12Bold = new Font("Arial", Font.PLAIN, 10);
            g2d.setFont(font12Bold);
            String user = saleInfo.getUsername();
        // String dateCurrently = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            LocalDateTime dateTimeCurrently = LocalDateTime.now();
            String dateCurrently = dateTimeCurrently.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            //String userInfoText = "username: " + user + "   fecha: " + dateCurrently;
            String userInfoText =  user + "   fecha: " + dateCurrently;
            g2d.drawString(userInfoText, 0, 2 * font12.getSize());

            // Establecer la fuente y el tamaño de la letra para el texto "Mesa: 1     Nro: 323"
            Font font16 = new Font("Arial", Font.PLAIN, 16);
            g2d.setFont(font16);
            String table = saleInfo.getNro_mesa();
            Integer nroPedido = saleInfo.getNro_pedido();
            String mesaText = "Mesa: " + table;
            if(place.equals(COCINA)){
                mesaText += " Nro: " + nroPedido;
            }
            g2d.drawString(mesaText, 0, 3 * font16.getSize());

            // Establecer la fuente y el tamaño de la letra para la tabla
            Font fontTable = new Font("Arial", Font.PLAIN, 12);
            g2d.setFont(fontTable);

       

            // Definir los datos de la tabla
            System.out.println("Printer one");
            String[][] data = new String[orders.size() + 1][3];
            data[0] = new String[]{"Nombre", "Can.", "Precio"};
            for (int i = 0; i < orders.size(); i++) {
                OrderDetailDTO order = orders.get(i);
                String nombre = order.getNombre();
                int cantidad = order.getCantidad();
                double precioUnitario = Double.parseDouble(order.getPrecio_venta());
                double precioTotal = cantidad * precioUnitario;
                data[i + 1] = new String[] {nombre, String.valueOf(cantidad), String.valueOf(precioTotal)};
            }

            // Calcular el ancho máximo de cada columna
            int[] columnWidths = new int[data[0].length];
            for (int i = 0; i < data[0].length; i++) {
                int maxWidth = 0;
                for (int j = 0; j < data.length; j++) {
                    int valueWidth = g2d.getFontMetrics().stringWidth(data[j][i]);
                    if (valueWidth > maxWidth) {
                        maxWidth = valueWidth;
                    }
                }
                columnWidths[i] = maxWidth;
            }

            // Calcular el ancho total de la tabla
            int totalWidth = 0;
            for (int width : columnWidths) {
                totalWidth += width;
            }

            // Ajustar el ancho de las columnas proporcionalmente para que encajen dentro de la tabla
            for (int i = 0; i < columnWidths.length; i++) {
                columnWidths[i] = (int) ((double) columnWidths[i] / totalWidth * (pageWidth)); // Ajuste de posición en X
            }

            // Calcular la posición para dibujar la tabla
            int x = 0; // Ajuste de posición en X
            int y = 6 * fontTable.getSize();

            // Dibujar los encabezados de la tabla
            int currentX = x;
            for (int i = 0; i < data[0].length; i++) {
                String header = data[0][i];
                g2d.drawString(header, currentX, y);
                currentX += columnWidths[i];
            }

            // Dibujar las filas de la tabla
            int currentY = y + fontTable.getSize();
            for (int i = 1; i < data.length; i++) {
                currentX = x;
                for (int j = 0; j < data[i].length; j++) {
                    String value = data[i][j];
                    g2d.drawString(value, currentX, currentY);
                    currentX += columnWidths[j];
                }
                currentY += fontTable.getSize();
            }

            // Establecer la fuente y el tamaño de la letra para el texto "Total=154"
            if(place.equals(SALON)){
                Font fontTotal = new Font("Arial", Font.PLAIN, 12);
                g2d.setFont(fontTotal);
                String totalText = "Total " + saleInfo.getCantidad_total();
                int totalWidth2 = g2d.getFontMetrics().stringWidth(totalText);
                int totalX = (int) (pageWidth - totalWidth2);
                g2d.drawString(totalText, totalX, (int) (currentY + fontTotal.getSize()));
                currentY += fontTotal.getSize();
            }
            /* Agregar nota */
            if(place.equals(COCINA) || place.equals(BAR)){
                String note = (String) saleInfo.getNote();
                if(note != null && note.length() > 0){
                    Font fontNote = new Font("Arial", Font.PLAIN, 12);
                    g2d.drawString(note,0, currentY + fontNote.getSize() );
                }
            }
            if(place.equals(SALON)){
                String note = (String) saleInfo.getNote();
                if(note != null && note.length() > 0){
                    Font fontNote = new Font("Arial", Font.PLAIN, 12);
                    g2d.drawString(note,0, currentY + fontNote.getSize() );
                }
            }
            return Printable.PAGE_EXISTS;
        } else {
            return Printable.NO_SUCH_PAGE;
        }
    }, pageFormat);


    try {
        printerJob.print();
    } catch (PrinterException e) {
        e.printStackTrace();
    }

    }
    public static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        for (PrintService printService : printServices) {
            if (printService.getName().equalsIgnoreCase(printerName)) {
                return printService;
            }
        }

        return null;
    }


    protected String printTest (String printerName) throws PrinterException {
        PrintService printService = findPrintService(printerName);

        String jsonResponse;
        if (printService == null) {
            jsonResponse = "{\"success\": false, \"message\": \"No se encontro la impresora\"}";
        }else{
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintService(printService);
            
            Paper paper = new Paper();
            paper.setSize(200, 500);
    
            PageFormat pageFormat = new PageFormat();
            pageFormat.setPaper(paper);
    
            
            printerJob.setPrintable((graphics, pf, pageIndex) -> {
                if (pageIndex == 0 ) { 
                    Graphics2D g2d = (Graphics2D) graphics;
                    String text = "Test de impresión";
                    Font font12 = new Font("Arial", Font.PLAIN, 12);
                    g2d.drawString(text, 0, font12.getSize());
                    return Printable.PAGE_EXISTS;
                } else {
                    return Printable.NO_SUCH_PAGE;
                }
            }, pageFormat);
        
            try {
                printerJob.print();
                jsonResponse = "{\"success\": true, \"message\": \"Imprimiendo\"}";
            } catch (PrinterException e) {
                e.printStackTrace();
                jsonResponse = "{\"success\": false, \"message\": \"Ocurrio un error al imprimir\"}";
            }
        }
        return jsonResponse;
    }
}
