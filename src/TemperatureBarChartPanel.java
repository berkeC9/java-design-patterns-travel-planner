import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class TemperatureBarChartPanel extends JPanel implements WeatherObserver {

    public TemperatureBarChartPanel() {
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        List<City> cities = CityRepository.getInstance().getCities();
        if (cities == null || cities.isEmpty()) return;

        int width = getWidth();
        int height = getHeight();
        
        // Dynamic width calculation to prevent overflow
        int leftPadding = 40;
        int rightPadding = 20;
        int spacing = 10;
        int availableWidth = width - leftPadding - rightPadding;
        int barWidth = (availableWidth - (cities.size() - 1) * spacing) / cities.size();
        
        // Draw axes
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawLine(30, 10, 30, height - 30); 
        g2d.drawLine(30, height - 30, width - 10, height - 30); 

        int x = leftPadding;
        for (City city : cities) {
            double exactTemp = city.getCurrentTemperature();
            int temp = (int) exactTemp;
            
            // Multiplier set to 4 instead of 5 to avoid vertical clipping
            int barHeight = Math.max(0, temp * 4); 

            g2d.setColor(new Color(220, 50, 50)); 
            g2d.fillRect(x, height - 30 - barHeight, barWidth, barHeight);

            // Center first 3 letters of city name below the bar
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 10));
            String label = city.getName().substring(0, Math.min(3, city.getName().length()));
            int labelWidth = g2d.getFontMetrics().stringWidth(label);
            g2d.drawString(label, x + (barWidth - labelWidth) / 2, height - 15);

            // Center temp value above the bar
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 10)); 
            String tempString = String.format(Locale.US, "%.1f", exactTemp); 
            int textWidth = g2d.getFontMetrics().stringWidth(tempString);
            
            // Adjust Y position for negative temperatures so they remain visible
            int textY = height - 35 - barHeight;
            if (exactTemp < 0) textY = height - 35; 
            
            g2d.drawString(tempString, x + (barWidth - textWidth) / 2, textY);

            x += barWidth + spacing;
        }
    }
}
