package model;

import view.ViewSimulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    public int numberOfClients;
    public int numberOfQueues;
    public int simulationInterval;
    public int currentTime = 0;
    public int averageWaitingTime;
    public int averageClients=0;
    public int minimumArrival;
    public int maximumArrival;
    public int minimumService;
    public int maximumService;
    public int sem = 1;
    int queue = 0;

    public int addTask(SimulationManager simulationManager, List<Queue> queues, Client c ) {
        queue = simulationManager.getMinimumQueue();
        queues.get(queue).addNewClient(c);
        return queues.size();
    }

    //afisarea rezultatelor in interfata
    public String printResult(List<Queue> queues, int nrOfQueues, int time, ArrayList<Client> waitingClients, int nrOfClients, FileWriter myWriter) {
        String toPrint;
        toPrint = "";
        try {
            myWriter.write("Time: " + time + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        toPrint = toPrint + "Time: " + time + "\n";
        try {
            myWriter.write("Waiting clients: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        toPrint = toPrint + "Waiting clients: ";
        for(int j=0; j<waitingClients.size(); j++){
            Client client = waitingClients.get(j);
            try {
                myWriter.write("("+client.getId()+" "+client.getArrivingTime()+" "+client.getServingTime()+")");
            } catch (IOException e) {
                e.printStackTrace();
            }
            toPrint = toPrint + "("+client.getId()+" "+client.getArrivingTime()+" "+client.getServingTime()+")";
        }
        try {
            myWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        toPrint = toPrint + "\n";
        int i = 1;
        while(i <= nrOfQueues) {
            try {
                myWriter.write("Queue " + i + ":");
            } catch (IOException e) {
                e.printStackTrace();
            }
            toPrint = toPrint + "Queue " + i + ":";
            if(queues.get(i-1).getSize()==0) {
                try {
                    myWriter.write("closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                toPrint = toPrint + "closed";
            }
            else {
                for(Client client: queues.get(i-1).listOfClients) {
                    int servTime;
                    servTime = client.getServingTime();
                    servTime += 1;
                    try {
                        myWriter.write("("+client.getId()+" "+client.getArrivingTime()+" "+ servTime +")");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    toPrint = toPrint + "("+client.getId()+" "+client.getArrivingTime()+" "+ servTime +")";
                }
            }
            try {
                myWriter.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            toPrint = toPrint + "\n";
            i++;
        }
        return toPrint;
    }

    //calculeaza average time-ul
    public void calculateAverageTime(SimulationManager manager, int currentTime) {
        int waitingCurrentTime = 0;
        int i = 0;
        int j = 0;
        while(i < manager.getQueueList().size() ){
            if (manager.getQueueList().get(i).numberOfClients != 0){
                averageClients++;
                waitingCurrentTime += manager.getQueueList().get(i).waitingTime.get();
            }
            i++;
        }
        averageWaitingTime += waitingCurrentTime;
        if( currentTime == manager.getSimulationInterval() ) {
            while(j < manager.getNrOfQueues()) {
                manager.getQueueList().get(j).timer = manager.getSimulationInterval();
                j++;
            }            
        }
    }

    public String printAverageTime(SimulationManager c, FileWriter myWriter) {
        String toPrint;
        toPrint = "";
        int i = 0;
        while(i <0) {
            if( c.getQueueList().get(i).numberOfClients != 0 ) {
                sem = 0;
            }
            i++;
        }
        if( sem == 1) {
            double avgTime;
            avgTime = (double) averageWaitingTime /averageClients;
            try {
                myWriter.write("\nAverage Waiting Time: " + Double.toString(avgTime));
            } catch (IOException e) {
                e.printStackTrace();
            }
            toPrint = toPrint + "\nAverage Waiting Time: " + Double.toString(avgTime);
        }
        return toPrint;
    }

    //scrierea in fisierul de iesire/afisarea in interfata
    public void run() {
        ViewSimulator viewSimulator;
        viewSimulator = new ViewSimulator();
        String toPrint1;
        toPrint1 = "";
        String toPrint2;
        toPrint2 = "";
        String string;
        String string2;
        FileWriter myWriter;
        SimulationManager manager;
        try {
            myWriter = new FileWriter("D:\\an2-sem2\\TP\\LAB - TEMA 2\\PT_30221_Bartalus_Izabella_Assignment_2\\src\\out_4.txt");
            manager = new SimulationManager(numberOfClients, numberOfQueues, simulationInterval, minimumArrival, maximumArrival, minimumService, maximumService);
            while (currentTime <= manager.getSimulationInterval()) {
                try {
                    if ( (manager.getListOfWaitingClients().size() != 0) && (manager.getListOfWaitingClients().get(0).getArrivingTime() == currentTime)) {
                        while (manager.getListOfWaitingClients().get(0).getArrivingTime() == currentTime ) {
                            addTask(manager, manager.queueList, manager.listOfWaitingClients.get(0));
                            manager.getListOfWaitingClients().remove(0);
                            if(manager.getListOfWaitingClients().size() == 0) break;
                        }}
                    Thread.sleep(1000);
                    toPrint1 = printResult(manager.getQueueList(),manager.getNrOfQueues(),currentTime,manager.getListOfWaitingClients(),manager.getNrOfClients(),myWriter);
                    string = convertToMultiline(toPrint1);
                    viewSimulator.text.setText(string);
                    currentTime++;
                    calculateAverageTime(manager,currentTime);
                }catch(InterruptedException e) {
                }
            }
            toPrint2 = printAverageTime(manager,myWriter);
            string = convertToMultiline(toPrint1);
            string2=convertToMultiline(toPrint2);
            String ss=string+string2;
            viewSimulator.text.setText(ss);
            myWriter.close();
        } catch (FileNotFoundException e1) {} catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public void setNumberOfQueues(int numberOfQueues) {
        this.numberOfQueues = numberOfQueues;
    }

    public void setSimulationInterval(int simulationInterval) {
        this.simulationInterval = simulationInterval;
    }

    public void setMinimumArrival(int minimumArrival) {
        this.minimumArrival = minimumArrival;
    }

    public void setMaximumArrival(int maximumArrival) {
        this.maximumArrival = maximumArrival;
    }

    public void setMinimumService(int minimumService) {
        this.minimumService = minimumService;
    }

    public void setMaximumService(int maximumService) {
        this.maximumService = maximumService;
    }

    public static String convertToMultiline(String orig) {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}


