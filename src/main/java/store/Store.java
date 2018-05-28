package store;

import store.deliver.Deliver;
import store.deliver.DroneDeliver;
import store.deliver.QuickDeliver;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Store
{
    // 멤버변수
    private Queue<Deliver> deliver_ready;         // 대기중인 배달부 목록  (큐)
    private Queue<Deliver> deliver_work;          // 배달중인 배달부 목록  (큐)
    private List<Rate> rateList;                            // 평점 리스트       (배열)
    private List<Menu> orderList;                           // 매뉴 리스트       (배열)
    private String storeType;                               // 가게 종류
    private String storeName;                               // 가게 이름
    public String location;                                // 가게 위치

    // 생성자
    public Store(String storeType, String storeName)
    {
        String location[] = {"혜화동","명동","장충동","신당동","신사동","논현동"};
        rateList  = new ArrayList<>(20);
        orderList = new ArrayList<>(20);
        deliver_ready = new LinkedList<>();
        deliver_work  = new LinkedList<>();

        this.storeType = storeType;
        this.storeName = storeName;
        this.location = location[(int)(Math.random()*location.length)];       //location 배열 중의 랜덤한 값중 하나

        for(int i = 0 ; i < 4 ; i++)        // 기본적으로 모든 가게는 배달부가 종류별로 3개씩 있다.
        {                                   // .offer : 큐에 데이터를 집어넣기
            deliver_ready.offer(new Deliver());
            deliver_ready.offer(new DroneDeliver());
            deliver_ready.offer(new QuickDeliver());
        }
    }

    /**
     *  가게의 메뉴를 보여주는 함수
     */
    public void showMenu()
    {

    }

    /**
     * 배달을 보내는 함수
     * @param deliverType 사용자가 원하는 타입의 배달,
     */
    public void sendDeliver(int deliverType)   // 1. 일반 배달, 2. 퀵배달, 3. 드론배달
    {
        Deliver deliver = pickDeliver(deliverType);       // 대기중인 배달부 큐에서 데이터를 꺼낸다.
        try{
            deliver.deliverStart();
        }catch(InterruptedException e) {
            e.printStackTrace();
            System.out.println("배달 실패");
        }

        deliver_ready.offer(deliver);   // 큐에서 꺼냇던 배달부는 다시 큐에 집어넣는다
    }

    /**
     * 사용자가 원하는 타입의 배달부를 대기중인 배달부 큐에서 꺼내는 함수
     *
     * @param   deliverType 어떤 배달을 시킬지에 대한 값
     * @return  해당 유형의 배달부
     */
    private Deliver pickDeliver(int deliverType)
    {
        switch(deliverType)                                 // .peek : 큐의 제일 앞을 확인, .offer : 큐에 데이터를 집어넣기 ,
        {                                                   // .poll : 큐의 제일 앞의 값을 삭제하고 그 값을 반환
            case 1: // 일반 배달부를 고를 때
                while(!(deliver_ready.peek() instanceof DroneDeliver
                        || !(deliver_ready.peek() instanceof QuickDeliver))) // 큐 제일 앞이 QuickDeliver나 DroneDeliver이 아닐때 까지
                {                                                            // 계속 뽑고 다시 넣기를 반복
                    Deliver deliver = deliver_ready.poll();
                    deliver_ready.offer(deliver);
                }
                return deliver_ready.poll();
            case 2: //  퀵 배달을 고를 때
                while(!(deliver_ready.peek() instanceof QuickDeliver))  // 큐 제일 앞이 QuickDeliver가 아닐때 까지 계속 뽑고 다시 넣기를 반복
                {
                    Deliver deliver = deliver_ready.poll();
                    deliver_ready.offer(deliver);
                }
                return deliver_ready.poll();
            case 3: // 드론배달을 고를 때
                while(!(deliver_ready.peek() instanceof DroneDeliver)) // 큐 제일 앞이 DroneDeliver가 아닐때 까지 계속 뽑고 다시 넣기를 반복
                {
                    Deliver deliver = deliver_ready.poll();
                    deliver_ready.offer(deliver);
                }
                return deliver_ready.poll();
            default:
                break;
        }
        return null;
    }



    /**
     * 배달이 종료될때의 함수
     */
    public void deliverEnd()
    {

    }

}