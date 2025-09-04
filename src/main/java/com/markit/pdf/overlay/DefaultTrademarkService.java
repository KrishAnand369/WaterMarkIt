package com.markit.pdf.overlay;

import com.markit.api.WatermarkAttributes;
import com.markit.api.positioning.WatermarkPositionCoordinates;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.util.Matrix;

import java.io.IOException;

/**
 * @author Oleg Cheban
 * @since 1.0
 */
public class DefaultTrademarkService implements TrademarkService {

    private static final String TRADEMARK_SYMBOL = "®";

    @Override
    public int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    public void overlayTrademark(PDPageContentStream contentStream, WatermarkAttributes attr, WatermarkPositionCoordinates.Coordinates c) throws IOException {
        final int trademarkFontSize = attr.getSize() / 2;

        contentStream.beginText();
        contentStream.setFont(attr.getPdfFont(), trademarkFontSize);
        contentStream.setNonStrokingColor(attr.getColor());
        contentStream.setTextMatrix(setTransformationMatrix(c, attr.getPdfWatermarkTextWidth(), attr.getPdfWatermarkTextHeight(), attr.getRotationDegrees()));
        contentStream.showText(TRADEMARK_SYMBOL);
        contentStream.endText();
    }

    private Matrix setTransformationMatrix(WatermarkPositionCoordinates.Coordinates c, float textWidth, float textHeight, int rotationDegrees) {
        Matrix matrix = new Matrix();
        matrix.translate(c.getX() + textWidth / 2, c.getY() + textHeight / 2);
        rotate(matrix, rotationDegrees);
        matrix.translate(textWidth / 2, textHeight / 2);
        return matrix;
    }

    private void rotate(Matrix matrix, int rotationDegrees){
        if (rotationDegrees != 0){
            matrix.rotate(Math.toRadians(rotationDegrees));
        }
    }
}
