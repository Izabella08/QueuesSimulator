package model;

import view.ViewSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class SimulationManager {
    private  int numberOfClients;
    private  int numberOfQueues;
    private  int simulationInterval;
    private  int minimumArrivingTime;
    private  int maximumArrivingTime;
    private  int minimumWaitingTime;
    private  int maximumWaitingTime;
    public ArrayList<Client> listOfWaitingClients = new ArrayList<Client>();
    public List<Queue> queueList = Collections.synchronizedList(new ArrayList<Queue>());

    //citirea din fisier
    public SimulationManager() throws FileNotFoundException{
        File file;
        file = new File("D:\\an2-sem2\\TP\\LAB - TEMA 2\\PT_30221_Bartalus_Izabella_Assignment_2\\src\\in_3.txt");
        Scanner myReader;
        myReader = new Scanner(file);
        this.numberOfClients = myReader.nextInt();
        this.numberOfQueues = myReader.nextInt();
        this.simulationInterval = myReader.nextInt();
        String time;
        time = myReader.next();
        String[] minMax;
        minMax = time.split(",");
        this.minimumArrivingTime = Integer.parseInt(minMax[0]);
        this.maximumArrivingTime = Integer.parseInt(minMax[1]);
        time = myReader.next();
        minMax = time.split(",");
        this.minimumWaitingTime = Integer.parseInt(minMax[0]);
        this.maximumWaitingTime = Integer.parseInt(minMax[1]);
        myReader.close();
        this.listOfWaitingClients =(ArrayList<Client>) generateClients(this.numberOfClients,this.numberOfQueues,this.minimumArrivingTime,this.maximumArrivingTime,this.minimumWaitingTime,this.maximumWaitingTime,this.listOfWaitingClients);
        initQueues(queueList, numberOfQueues,simulationInterval);
    }

    public SimulationManager(ViewSimulator view){

    }

    //generarea random de clienti
    public List<Client> generateClients(int nrClients, int nrQueues, int minArrivingTime, int maxArrivingTime, int minWaitingTime, int maxWaitingTime, ArrayList<Client> waitingClients) {
        int i = 0;
        Client client;
        Random random;
        int arrivingTime;
        Random random1;
        int servingTime;
        while(i < nrClients) {
            client = new Client();
            client.setId(i+1);
            random = new Random();
            arrivingTime = this.minimumArrivingTime +random.nextInt(this.maximumArrivingTime -this.minimumArrivingTime +1);
            client.setArrivingTime(arrivingTime);
            random1 = new Random();
            servingTime = this.minimumWaitingTime + random1.nextInt(this.maximumWaitingTime -this.minimumWaitingTime +1);
            client.setServingTime(servingTime);
            this.listOfWaitingClients.add(client);
            i++;
        }
        return waitingClients;
    }

    //afla coada minima
    public synchronized Integer getMinimumQueue()
    {
        int foundQueue =0;
        List<Client> listOfClients2;
        int timer1 = 0;
        int timer2 = 0;
        int minimumServTime = 0;
        int minQueue;
        int i = 1;
        int k = 0;
        LinkedBlockingDeque<Client> minQueue2;
        List<Client> clientList;
        minQueue = this.queueList.get(0).getSize();
        int j = 0;
        while( j<this.numberOfQueues) {
            if (this.queueList.get(j).getSize() == 0)
                return j;
            j++;
        }
        clientList = new ArrayList<>(this.queueList.get(0).listOfClients);
        while( k < this.queueList.get(0).listOfClients.size()){
            minimumServTime = minimumServTime + clientList.get(k).getServingTime();
            k++;
        }
        minQueue2 = this.queueList.get(0).listOfClients;
        listOfClients2 = new ArrayList<>(this.queueList.get(0).listOfClients);
        while(i < this.numberOfQueues) {
            if(this.queueList.get(i).getSize() < minQueue)
            {
                Queue getMinQ = this.queueList.get(i);
                minQueue = getMinQ.getSize();
                Queue getMinQ2 = this.queueList.get(i);
                minQueue2 = getMinQ2.listOfClients;
                int index = 0;
                while(index < getMinQ.listOfClients.size()) {
                    minimumServTime = minimumServTime + listOfClients2.get(index).getServingTime();
                    index++;
                }
                foundQueue = i;
            }
            if(this.queueList.get(i).getSize() == minQueue)
            {
                timer1 = 0;
                int index2 = 0;
                Queue getMinQ = this.queueList.get(i);
                while(index2 < getMinQ.listOfClients.size()){
                    timer1 = timer1 + listOfClients2.get(index2).getServingTime();
                    index2++;
                }
                if(timer1 < minimumServTime) {
                    minQueue = getMinQ.getSize();
                    minimumServTime = timer1;
                    foundQueue = i;
                }
            }
            i++;
        }
        return foundQueue;
    }

    public void initQueues(List<Queue> queues, int queuesNumber, int time) {
        Queue queue;
        int i = 0;
        int j = 0;
        while(i < queuesNumber){
            queue = new Queue(time);
            this.queueList.add(queue);
            i++;
        }
        while(j < queuesNumber) {
            this.queueList.get(j).startQueue();
            j++;
        }
    }

    public SimulationManager(int nrClientsGui, int nrQueuesGui,int simulationIntervalGui, int minArrivingTimeGui, int maxArrivingTimeGui, int minServiceTimeGui, int maxServiceTimeGui){
        this.numberOfClients =nrClientsGui;
        this.numberOfQueues =nrQueuesGui;
        this.simulationInterval=simulationIntervalGui;
        this.minimumArrivingTime =minArrivingTimeGui;
        this.maximumArrivingTime =maxArrivingTimeGui;
        this.minimumWaitingTime =minServiceTimeGui;
        this.maximumWaitingTime =maxServiceTimeGui;
        this.listOfWaitingClients =(ArrayList<Client>) generateClients(this.numberOfClients,this.numberOfQueues,this.minimumArrivingTime,this.maximumArrivingTime,this.minimumWaitingTime,this.maximumWaitingTime,this.listOfWaitingClients);
        initQueues(queueList, numberOfQueues,simulationInterval);
    }

    public int getMaximumWaitingTime() {
        return this.maximumWaitingTime;
    }

    public int getSimulationInterval() {
        return this.simulationInterval;
    }

    public void setSimulationIntervat(int t) {
        this.simulationInterval=t;
    }

    public int getNrOfClients() {
        return this.numberOfClients;
    }

    public ArrayList<Client> getListOfWaitingClients(){
        return this.listOfWaitingClients;
    }

    public int getNrOfQueues() {
        return this.numberOfQueues;
    }

    public List<Queue> getQueueList(){
        return this.queueList;
    }
}


