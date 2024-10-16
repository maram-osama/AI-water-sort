package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GUI extends JFrame {
    private JPanel[] bottles;
    private String[][] bottleState;
    private Queue<String> pours;
    private int pathCost;
    private int numExpansions;
    private JLabel stepLabel;
    private int stepCounter = 0;
    private JLabel stepInfoLabel;
    
    public GUI(String init, String path, String strategy) {
        setTitle("Water Sort Visualization");
        setLayout(new BorderLayout());
    
        if (path.equals("NOSOLUTION")) {
            showNoSolution();
            return;
        }
        
        parseInitState(init);
        String[] splits = path.split(";");
        pours = new LinkedList<>(Arrays.asList(splits[0].split(",")));
        pathCost = Integer.parseInt(splits[1]);
        numExpansions = Integer.parseInt(splits[2]);
        
        stepLabel = new JLabel("Step: 0", SwingConstants.CENTER);
        stepLabel.setFont(new Font("Arial", Font.BOLD, 18));
        stepLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        stepLabel.setBackground(new Color(173, 216, 230));
        stepLabel.setOpaque(true);
    
        stepInfoLabel = new JLabel("", SwingConstants.CENTER);
        stepInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        stepInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        stepInfoLabel.setBackground(new Color(173, 216, 230));
        stepInfoLabel.setOpaque(true);
    
        JLabel strategyLabel = new JLabel("Strategy: " + strategy, SwingConstants.CENTER);
        strategyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        strategyLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        strategyLabel.setBackground(new Color(173, 216, 230));
        strategyLabel.setOpaque(true);
    
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(173, 216, 230));
        topPanel.add(stepLabel, BorderLayout.WEST);
        topPanel.add(strategyLabel, BorderLayout.EAST);
    
        JPanel bottlesPanel = new JPanel(new GridLayout(1, bottleState.length, 15, 0));
        bottlesPanel.setBackground(new Color(173, 216, 230));
        bottlesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        bottles = new JPanel[bottleState.length];
        for (int i = 0; i < bottleState.length; i++) {
            bottles[i] = createBottlePanel(bottleState[i]);
            bottlesPanel.add(bottles[i]);
        }
        
        JButton nextButton = new JButton("Next Step");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setBackground(new Color(40, 129, 238));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pours.isEmpty()) {
                    stepCounter++;
                    stepLabel.setText("Step: " + stepCounter);
                    performNextStep(pours.poll());
                } else {
                    JOptionPane.showMessageDialog(null, "You won! \nPath cost: " + pathCost + "\nNumber of expansions: "+ numExpansions);
                    dispose();
                }
            }
        });
        
        JPanel buttonPanel = new JPanel(new GridLayout(2,1));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(stepInfoLabel);
        buttonPanel.add(nextButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(bottlesPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void showNoSolution() {
        ImageIcon wallpaper = new ImageIcon("src/images/sad.jpg");
        
        JLabel noSolutionLabel = new JLabel("No solution :(", wallpaper, SwingConstants.CENTER);
        noSolutionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        noSolutionLabel.setHorizontalTextPosition(JLabel.CENTER);
        noSolutionLabel.setVerticalTextPosition(JLabel.CENTER);
        noSolutionLabel.setForeground(Color.RED);
        
        add(noSolutionLabel);
        
        setSize(236, 231);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
    private void parseInitState(String init) {
        String[] parts = init.split(";");
        int numBottles = Integer.parseInt(parts[0]);
        int layersPerBottle = Integer.parseInt(parts[1]);
        
        bottleState = new String[numBottles][layersPerBottle];
        for (int i = 0; i < numBottles; i++) {
            bottleState[i] = parts[i + 2].split(",");
        }
    }
    
    private JPanel createBottlePanel(String[] layers) {
        JPanel bottle = new JPanel(new GridLayout(layers.length, 1, 5, 5));
        bottle.setBackground(new Color(220, 220, 220));
        bottle.setPreferredSize(new Dimension(60, 150));
        bottle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        
        for (String color : layers) {
            JPanel layer = new JPanel();
            layer.setBackground(getColor(color));
            layer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bottle.add(layer);
        }
        return bottle;
    }
    
    private void performNextStep(String step) {
        String[] parts = step.split("_");
        int from = Integer.parseInt(parts[1]);
        int to = Integer.parseInt(parts[2]);
        
        int fromLayer = findTopLayer(from);
        if (fromLayer == -1) return;
    
        stepInfoLabel.setText("From bottle " + from + " to bottle " + to);
        
        String colorToPour = bottleState[from][fromLayer];
        
        int layersToPour = 0;
        for (int i = fromLayer; i < bottleState[from].length; i++) {
            if (bottleState[from][i].equals(colorToPour)) {
                layersToPour++;
            } else {
                break;
            }
        }
        
        int toLayer = findEmptyLayer(to);
        if (toLayer == -1) return;
        
        int availableSpace = toLayer + 1;
        if (availableSpace < layersToPour) {
            layersToPour = availableSpace;
        }
        
        for (int i = 0; i < layersToPour; i++) {
            bottleState[to][toLayer - i] = colorToPour;
            bottleState[from][fromLayer + i] = "e";
        }
        
        updateBottlePanel(from);
        updateBottlePanel(to);
    }
    
    private int findTopLayer(int bottleIndex) {
        for (int i = 0; i < bottleState[bottleIndex].length; i++) {
            if (!bottleState[bottleIndex][i].equals("e")) {
                return i;
            }
        }
        return -1;
    }
    
    private int findEmptyLayer(int bottleIndex) {
        for (int i = bottleState[bottleIndex].length - 1; i >= 0; i--) {
            if (bottleState[bottleIndex][i].equals("e")) {
                return i;
            }
        }
        return -1;
    }
    
    private void updateBottlePanel(int bottleIndex) {
        JPanel bottle = bottles[bottleIndex];
        bottle.removeAll();
        for (String color : bottleState[bottleIndex]) {
            JPanel layer = new JPanel();
            layer.setBackground(getColor(color));
            layer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bottle.add(layer);
        }
        bottle.revalidate();
        bottle.repaint();
    }
    
    private Color getColor(String color) {
        switch (color) {
            case "r":
                return new Color(255, 107, 107);
            case "g":
                return new Color(107, 203, 119);
            case "b":
                return new Color(77, 150, 255);
            case "y":
                return new Color(240, 217, 61);
            case "o":
                return new Color(255, 175, 0);
            case "e":
                return new Color(240, 240, 240);
            default:
                return Color.GRAY;
        }
    }
}
