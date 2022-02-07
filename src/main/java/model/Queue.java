package model;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue extends Thread{
    public AtomicInteger waitingTime;
    public int numberOfClients;
    public int closingTime;
    public int timer = 0;
    public Thread thread;
    public LinkedBlockingDeque<Client> listOfClients = new LinkedBlockingDeque<Client>(2000);

    public Queue(Integer stopTimeForQueue) {
        this.closingTime = stopTimeForQueue;
        waitingTime = new AtomicInteger(0);
    }

    //stergerea unui client din coada
    public synchronized void deleteClient() {
        while (listOfClients.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.listOfClients.remove(0);
        this.numberOfClients--;
        notifyAll();
    }

    //adaugarea unui client in coada
    public void addNewClient(Client client) {
        this.numberOfClients++;
        if(this.numberOfClients > 1)
            client.setServingTime(client.getServingTime()-1);
        this.listOfClients.add(client);
        waitingTime.addAndGet(client.getServingTime());
    }

    public LinkedBlockingDeque<Client> returnQueue() {
        return this.listOfClients;
    }

    public int getSize() {
        return this.listOfClients.size();
    }

    public synchronized void run()  {
        Client client;
        int servTime;
        int i = 0;
        while((listOfClients.size() != 0) || (timer < closingTime)) {
            try {
                Thread.sleep(1500);
                if(this.listOfClients.size() != 0) {
                    client = this.listOfClients.peek();
                    servTime = client.getServingTime();
                    while(i < servTime){
                        client.setServingTime(client.getServingTime()-1);
                        Thread.sleep(1000);
                        timer++;
                        this.waitingTime.decrementAndGet();
                        i++;
                    }
                    this.deleteClient();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startQueue() {
        thread = new Thread(this);
        thread.start();
    }

}