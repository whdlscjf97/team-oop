package store.deliver;

public class Deliver
{
    // 멤버변수
    protected int     speed;        // 배달원의 속력
    protected boolean onDeliver;    // 배달중인지 아닌지

    public Deliver()
    {
        this.speed = 0;
        this.onDeliver = false;
    }

    public void deliverStart() throws InterruptedException
    {
        int i = 0;
        System.out.println("배달을 시작합니다");
        while( i < 5)
        {
            Thread.sleep(1000);
            if(i == 2)
                System.out.println("알림 : 배달 도착까지 5분 남았습니다");
            i++;
        }
        System.out.println("배달이 완료되었습니다");
    }
}
