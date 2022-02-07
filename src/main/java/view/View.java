package view;

import model.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame{

    public JLabel nrOfClients;
    public JTextField nrOfClientsText;
    public JLabel nrOfQueues;
    public JTextField nrOfQueuesText;
    public JLabel simulationInterval;
    public JTextField simulationIntervalText;
    public JLabel minArrival;
    public JTextField minArrivalText;
    public JLabel maxArrival;
    public JTextField maxArrivalText;
    public JLabel minService;
    public JTextField minServiceText;
    public JLabel maxService;
    public JTextField maxServiceText;
    public JButton startButton;
    private JTextArea area2;

    public View() {
        this.setSize(700, 600);
        this.setTitle("QUEUES SIMULATOR");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        //setez backgoundul frame-ului
        this.getContentPane().setBackground(new Color(100, 100, 100));

        JLabel title = new JLabel("QUEUES SIMULATOR");
        title.setBounds(210, 20, 400, 50);
        title.setForeground(new Color(255, 255, 255));
        title.setBackground(new Color(255, 157, 203));
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setVisible(true);
        this.add(title);

        //label pt introducerea numarului de clienti
        nrOfClients = new JLabel("Number of clients:");
        nrOfClients.setFont(new Font("Arial",Font.BOLD,20));
        nrOfClients.setBounds(20,10,200,150);
        nrOfClients.setForeground(new Color(255,255,255));
        this.getContentPane().add(nrOfClients);
        nrOfClientsText = new JTextField(20);
        nrOfClientsText.setBounds(20,100,230,30);
        this.getContentPane().add(nrOfClientsText);

        //label pt introducerea numarului de cozi
        nrOfQueues = new JLabel("Number of queues:");
        nrOfQueues.setFont(new Font("Arial",Font.BOLD,20));
        nrOfQueues.setBounds(20,80,240,150);
        nrOfQueues.setForeground(new Color(255,255,255));
        this.getContentPane().add(nrOfQueues);
        nrOfQueuesText = new JTextField(20);
        nrOfQueuesText.setBounds(20,170,230,30);
        this.getContentPane().add(nrOfQueuesText);

        //label pt introducerea SIMULATION INTERVAL
        simulationInterval = new JLabel("Simulation interval:");
        simulationInterval.setFont(new Font("Arial",Font.BOLD,20));
        simulationInterval.setBounds(20,150,240,150);
        simulationInterval.setForeground(new Color(255,255,255));
        this.getContentPane().add(simulationInterval);
        simulationIntervalText = new JTextField(20);
        simulationIntervalText.setBounds(20,240,230,30);
        this.getContentPane().add(simulationIntervalText);

        //label pt introducerea MINIMUM ARRIVAL TIME
        minArrival = new JLabel("Minimum arrival time:");
        minArrival.setFont(new Font("Arial",Font.BOLD,20));
        minArrival.setBounds(20,220,240,150);
        minArrival.setForeground(new Color(255,255,255));
        this.getContentPane().add(minArrival);
        minArrivalText = new JTextField(20);
        minArrivalText.setBounds(20,310,230,30);
        this.getContentPane().add(minArrivalText);

        //label pt introducerea MAXIMUM ARRIVAL TIME
        maxArrival = new JLabel("Maximum arrival time:");
        maxArrival.setFont(new Font("Arial",Font.BOLD,20));
        maxArrival.setBounds(20,290,240,150);
        maxArrival.setForeground(new Color(255,255,255));
        this.getContentPane().add(maxArrival);
        maxArrivalText = new JTextField(20);
        maxArrivalText.setBounds(20,380,230,30);
        this.getContentPane().add(maxArrivalText);

        //label pt introducerea MINIMUL SERVICE TIME
        minService = new JLabel("Minimum service time:");
        minService.setFont(new Font("Arial",Font.BOLD,20));
        minService.setBounds(20,360,240,150);
        minService.setForeground(new Color(255,255,255));
        this.getContentPane().add(minService);
        minServiceText = new JTextField(20);
        minServiceText.setBounds(20,450,230,30);
        this.getContentPane().add(minServiceText);

        //label pt introducerea MAXIMUM SERVICE TIME
        maxService = new JLabel("Maximum service time:");
        maxService.setFont(new Font("Arial",Font.BOLD,20));
        maxService.setBounds(20,430,240,150);
        maxService.setForeground(new Color(255,255,255));
        this.getContentPane().add(maxService);
        maxServiceText = new JTextField(20);
        maxServiceText.setBounds(20,520,230,30);
        this.getContentPane().add(maxServiceText);

        //buton de START
        startButton=new JButton("START!");
        startButton.setBackground(Color.WHITE);
        startButton.setBounds(400,100,170,30);
        startButton.setToolTipText("Click here to run the program!");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton){
                    Server server = new Server();
                    server.setNumberOfClients(getNrOfClientsText());
                    server.setNumberOfQueues(getNrOfQueuesText());
                    server.setSimulationInterval(getSimulationIntervalText());
                    server.setMinimumArrival(getMinArrivalText());
                    server.setMaximumArrival(getMaxArrivalText());
                    server.setMinimumService(getMinServiceText());
                    server.setMaximumService(getMaxServiceText());
                    Thread t = new Thread(server);
                    t.start();
                }
            }
        });

        this.add(startButton);

        this.setVisible(true);
    }


    public int getNrOfClientsText(){
        String s = nrOfClientsText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getNrOfQueuesText(){
        String s = nrOfQueuesText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getSimulationIntervalText(){
        String s = simulationIntervalText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getMinArrivalText(){
        String s = minArrivalText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getMaxArrivalText(){
        String s = maxArrivalText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getMinServiceText(){
        String s = minServiceText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getMaxServiceText(){
        String s = maxServiceText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }
}

