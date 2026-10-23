import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TravelPlannerApp extends JFrame {

    // UI Components
    private JComboBox<String> sortComboBox;
    private JComboBox<String> weatherComboBox;
    private JTextField searchField; 
    private JButton savePlanButton; 
    private DefaultListModel<City> allCitiesModel;
    private JList<City> allCitiesList;
    private DefaultListModel<City> filteredCitiesModel;
    private JList<City> filteredCitiesList;
    
    // Decorator UI Components
    private JCheckBox cbMuseum, cbShopping, cbPark, cbCenter;
    private JTextArea planDetailsArea;

    // State
    private List<City> cities;

    public TravelPlannerApp() {
        // Fetch data once from the Singleton repository
        cities = CityRepository.getInstance().getCities();

        // Base GUI setup
        setTitle("Travel Planner - City Sorter Application");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adding 15px gaps and margins to prevent a cluttered interface
        JPanel mainContentPane = new JPanel(new BorderLayout(15, 15));
        mainContentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(mainContentPane);

        // TOP PANEL (Controls)
        // Using a 3-row grid to accommodate the bonus search bar
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Controls"));

        // Search filter implementation via DocumentListener
        topPanel.add(new JLabel("Search City:"));
        searchField = new JTextField();
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateAllCitiesList(); }
            public void removeUpdate(DocumentEvent e) { updateAllCitiesList(); }
            public void changedUpdate(DocumentEvent e) { updateAllCitiesList(); }
        });
        topPanel.add(searchField);

        topPanel.add(new JLabel("Sort Cities:"));
        String[] sortOptions = {"Sort by Name", "Sort by Population", "Sort by Area"};
        sortComboBox = new JComboBox<>(sortOptions);
        topPanel.add(sortComboBox);

        topPanel.add(new JLabel("Filter by Weather:"));
        String[] weatherOptions = {"All", "Sunny", "Cloudy", "Rainy", "Snowy"};
        weatherComboBox = new JComboBox<>(weatherOptions);
        topPanel.add(weatherComboBox);

        add(topPanel, BorderLayout.NORTH);

        // MIDDLE PANEL (Lists & Planner)
        JPanel middlePanel = new JPanel(new GridLayout(1, 3, 10, 10));

        // Standardized font for better readability across all list/text components
        Font generalFont = new Font("SansSerif", Font.PLAIN, 14);

        allCitiesModel = new DefaultListModel<>();
        allCitiesList = new JList<>(allCitiesModel);
        allCitiesList.setFont(generalFont); 
        JScrollPane allCitiesScroll = new JScrollPane(allCitiesList);
        allCitiesScroll.setBorder(BorderFactory.createTitledBorder("All Cities (Sorted)"));
        middlePanel.add(allCitiesScroll);

        filteredCitiesModel = new DefaultListModel<>();
        filteredCitiesList = new JList<>(filteredCitiesModel);
        filteredCitiesList.setFont(generalFont); 
        JScrollPane filteredCitiesScroll = new JScrollPane(filteredCitiesList);
        filteredCitiesScroll.setBorder(BorderFactory.createTitledBorder("Cities by Weather"));
        middlePanel.add(filteredCitiesScroll);

        // Right Panel (Decorator Planner)
        JPanel plannerPanel = new JPanel(new BorderLayout());
        plannerPanel.setBorder(BorderFactory.createTitledBorder("City Visit Planner"));
        
        JPanel checkboxPanel = new JPanel(new GridLayout(4, 1));
        cbMuseum = new JCheckBox("Visit Museum");
        cbShopping = new JCheckBox("Visit Shopping Mall");
        cbPark = new JCheckBox("Walk in the Park");
        cbCenter = new JCheckBox("Visit City Center");

        cbMuseum.setFont(generalFont);
        cbShopping.setFont(generalFont);
        cbPark.setFont(generalFont);
        cbCenter.setFont(generalFont);
        
        checkboxPanel.add(cbMuseum);
        checkboxPanel.add(cbCenter);
        checkboxPanel.add(cbShopping);
        checkboxPanel.add(cbPark);
        
        planDetailsArea = new JTextArea();
        planDetailsArea.setEditable(false);
        planDetailsArea.setLineWrap(true);
        planDetailsArea.setWrapStyleWord(true);
        planDetailsArea.setFont(new Font("SansSerif", Font.PLAIN, 14)); 
        planDetailsArea.setMargin(new Insets(10, 10, 10, 10)); 
        planDetailsArea.setText("Select a city and activities to see the plan.");

        // Save Trip Plan functionality
        savePlanButton = new JButton("Save Trip Plan");
        savePlanButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        savePlanButton.addActionListener(e -> saveTripPlan());

        JPanel plannerBottomPanel = new JPanel(new BorderLayout());
        plannerBottomPanel.add(new JScrollPane(planDetailsArea), BorderLayout.CENTER);
        plannerBottomPanel.add(savePlanButton, BorderLayout.SOUTH);
        
        plannerPanel.add(checkboxPanel, BorderLayout.NORTH);
        plannerPanel.add(plannerBottomPanel, BorderLayout.CENTER); 
        
        middlePanel.add(plannerPanel);
        add(middlePanel, BorderLayout.CENTER);

        // BOTTOM PANEL (Charts & Observer Pattern)
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        TemperatureBarChartPanel barChartPanel = new TemperatureBarChartPanel();
        barChartPanel.setBorder(BorderFactory.createTitledBorder("City Temperatures"));
        
        WeatherPieChartPanel pieChartPanel = new WeatherPieChartPanel();
        pieChartPanel.setBorder(BorderFactory.createTitledBorder("Weather Conditions"));
        
        bottomPanel.add(barChartPanel);
        bottomPanel.add(pieChartPanel);
        
        add(bottomPanel, BorderLayout.SOUTH);

        // Observer Pattern Integration: Register UI components to the background thread
        WeatherStation weatherStation = new WeatherStation();
        weatherStation.attach(barChartPanel);
        weatherStation.attach(pieChartPanel);
        weatherStation.attach(() -> SwingUtilities.invokeLater(this::updateFilteredCitiesList));

        //  EVENT LISTENERS

        // Strategy Pattern Trigger: Sort list dynamically on combobox change
        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = sortComboBox.getSelectedIndex();
                SortStrategy strategy = null;

                if (selectedIndex == 0) strategy = new NameSortStrategy();
                else if (selectedIndex == 1) strategy = new PopulationSortStrategy();
                else if (selectedIndex == 2) strategy = new AreaSortStrategy();

                if (strategy != null) {
                    CityRepository.getInstance().sortCities(strategy);
                    cities = CityRepository.getInstance().getCities();
                    updateAllCitiesList();
                    updateFilteredCitiesList();
                    barChartPanel.repaint();
                }
            }
        });

        // Iterator Pattern Trigger: Filter list dynamically on weather selection
        weatherComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFilteredCitiesList();
            }
        });

        // Decorator Pattern Trigger: Recalculate plan on city selection or checkbox toggle
        ActionListener plannerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlanner();
            }
        };
        
        cbMuseum.addActionListener(plannerListener);
        cbShopping.addActionListener(plannerListener);
        cbPark.addActionListener(plannerListener);
        cbCenter.addActionListener(plannerListener);
        
        allCitiesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) updatePlanner();
        });

        // sort alphabetically and populate the UI
        CityRepository.getInstance().sortCities(new NameSortStrategy());
        cities = CityRepository.getInstance().getCities();
        updateAllCitiesList();
        updateFilteredCitiesList();

        // Run as a daemon thread so it terminates when the main UI closes
        Thread weatherThread = new Thread(weatherStation);
        weatherThread.setDaemon(true); 
        weatherThread.start();
    }

    // Refresh the list and apply the search filter
    private void updateAllCitiesList() {
        City previouslySelected = allCitiesList != null ? allCitiesList.getSelectedValue() : null;
        allCitiesModel.clear();
        String searchText = searchField != null ? searchField.getText().toLowerCase() : "";
        List<City> snapshot = new ArrayList<>(cities);
        
        for (City c : snapshot) {
            if (c.getName().toLowerCase().contains(searchText)) {
                allCitiesModel.addElement(c);
            }
        }
        if (previouslySelected != null && allCitiesModel.contains(previouslySelected)) {
            allCitiesList.setSelectedValue(previouslySelected, true);
        }
    }

    // Helper method to refresh the weather-filtered list (supports "All" and live Observer updates)
    private void updateFilteredCitiesList() {
        if (weatherComboBox == null || filteredCitiesModel == null) return;
        String selectedWeather = (String) weatherComboBox.getSelectedItem();
        if (selectedWeather == null) return;

        City previouslySelected = filteredCitiesList != null ? filteredCitiesList.getSelectedValue() : null;
        filteredCitiesModel.clear();
        List<City> snapshot = new ArrayList<>(cities);

        if ("All".equals(selectedWeather)) {
            for (City c : snapshot) {
                filteredCitiesModel.addElement(c);
            }
        } else {
            CityIterator iterator = null;
            switch (selectedWeather) {
                case "Sunny": iterator = new SunnyCityIterator(snapshot); break;
                case "Cloudy": iterator = new CloudyCityIterator(snapshot); break;
                case "Rainy": iterator = new RainyCityIterator(snapshot); break;
                case "Snowy": iterator = new SnowyCityIterator(snapshot); break;
            }

            if (iterator != null) {
                while (iterator.hasNext()) {
                    filteredCitiesModel.addElement(iterator.next());
                }
            }
        }

        if (previouslySelected != null && filteredCitiesModel.contains(previouslySelected)) {
            filteredCitiesList.setSelectedValue(previouslySelected, false);
        }
    }

    // Core Decorator logic: wraps the base visit plan dynamically based on user selections
    private void updatePlanner() {
        City selectedCity = allCitiesList.getSelectedValue();
        if (selectedCity == null) {
            planDetailsArea.setText("Please select a city from the 'All Cities' list first.");
            return;
        }

        // Base component
        VisitPlan plan = new BaseCityVisit(selectedCity);

        // Decorator chaining
        if (cbMuseum.isSelected()) plan = new MuseumVisit(plan);
        if (cbCenter.isSelected()) plan = new CityCenterVisit(plan);
        if (cbShopping.isSelected()) plan = new ShoppingMallVisit(plan);
        if (cbPark.isSelected()) plan = new ParkVisit(plan);

        // Aggregated result
        String details = plan.getDescription() + "\n\n" +
                         "Total Time: " + String.format(Locale.US, "%.1f", plan.getTime()) + " hours\n" +
                         "Total Cost: " + String.format(Locale.US, "$%.2f", plan.getCost());
        
        planDetailsArea.setText(details);
    }

    // Export functionality for the decorated trip plan
    private void saveTripPlan() {
        String planText = planDetailsArea.getText();
        if (planText.isEmpty() || planText.startsWith("Select a city") || planText.startsWith("Please select")) {
            JOptionPane.showMessageDialog(this, "There is no valid trip plan to save!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Your Trip Plan");
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }
            try (PrintWriter out = new PrintWriter(filePath, "UTF-8")) {
                out.println("=== MY CITY VISIT PLAN ===");
                out.println(planText);
                JOptionPane.showMessageDialog(this, "Trip plan successfully saved as a text file!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while saving the plan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Apply native OS Look and Feel for better UX
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Thread-safe UI initialization
        SwingUtilities.invokeLater(() -> {
            TravelPlannerApp app = new TravelPlannerApp();
            app.setLocationRelativeTo(null); 
            app.setVisible(true);
        });
    }
}
