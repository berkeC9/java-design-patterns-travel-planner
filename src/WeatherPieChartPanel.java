import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WeatherPieChartPanel extends JPanel implements WeatherObserver {

    public WeatherPieChartPanel() {
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
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        List<City> cities = CityRepository.getInstance().getCities();
        if (cities == null || cities.isEmpty()) return;

        int sunny = 0, cloudy = 0, rainy = 0, snowy = 0;

        for (City city : cities) {
            switch (city.getCurrentWeatherState()) {
                case SUNNY: sunny++; break;
                case CLOUDY: cloudy++; break;
                case RAINY: rainy++; break;
                case SNOWY: snowy++; break;
            }
        }

        int total = cities.size();
        int sunnyAngle = (int) Math.round((double) sunny / total * 360);
        int cloudyAngle = (int) Math.round((double) cloudy / total * 360);
        int rainyAngle = (int) Math.round((double) rainy / total * 360);
        
        // Assigning the remainder to the last slice to prevent rendering gaps caused by rounding
        int snowyAngle = 360 - (sunnyAngle + cloudyAngle + rainyAngle); 

        // Shifting the pie chart slightly left to accommodate the legend
        int width = 200;
        int height = 200;
        int x = (getWidth() / 2) - (width / 2) - 40; 
        int y = (getHeight() / 2) - (height / 2);

        int startAngle = 0;

        g2d.setColor(Color.YELLOW);
        g2d.fillArc(x, y, width, height, startAngle, sunnyAngle);
        startAngle += sunnyAngle;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillArc(x, y, width, height, startAngle, cloudyAngle);
        startAngle += cloudyAngle;

        g2d.setColor(Color.BLUE);
        g2d.fillArc(x, y, width, height, startAngle, rainyAngle);
        startAngle += rainyAngle;

        g2d.setColor(Color.WHITE);
        g2d.fillArc(x, y, width, height, startAngle, snowyAngle);
        
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(x, y, width, height); 

        // Legend positioning
        // Pulled away from the right wall and centered vertically
        int legendX = getWidth() - 110; 
        int legendY = (getHeight() / 2) - 40; 

        g2d.setFont(new Font("SansSerif", Font.BOLD, 11));

        drawLegendItem(g2d, "SUNNY", Color.YELLOW, legendX, legendY);
        drawLegendItem(g2d, "CLOUDY", Color.LIGHT_GRAY, legendX, legendY + 25);
        drawLegendItem(g2d, "RAINY", Color.BLUE, legendX, legendY + 50);
        drawLegendItem(g2d, "SNOWY", Color.WHITE, legendX, legendY + 75);
    }

    private void drawLegendItem(Graphics2D g2d, String label, Color color, int x, int y) {
        g2d.setColor(color);
        g2d.fillRect(x, y, 12, 12); 
        
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawRect(x, y, 12, 12); 
        
        g2d.setColor(Color.BLACK);
        g2d.drawString(label, x + 20, y + 11); 
    }
}
