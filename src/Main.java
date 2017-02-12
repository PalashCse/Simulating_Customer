import java.util.Random;
import java.util.Scanner;


public class Main {
	
	static Random ran=new Random();

	public static void main(String[] args) {
		
         Scanner sc= new Scanner(System.in);
         Method_Class s=new Method_Class();
		
        System.out.println("Enter Mean Interarrival time:");
        Method_Class.mean_interarrival=sc.nextDouble();
       
        System.out.println("Starting time for first server.........");
        double tbegininspect =sc.nextDouble();
        
        System.out.println("Leaving time for first server.........");
        double tendinspect=sc.nextDouble();
        
        System.out.println("Starting time for second server.........");
        double tbegin2ndservice=sc.nextDouble();
        
        System.out.println("Leaving time for second server.........");
        double tend2ndservice=sc.nextDouble();
        
        System.out.println("Starting time for third server.........");
        double tbegin3rdservice=sc.nextDouble();
        
        System.out.println("Leaving time for third server.........");
        double tend3rdservice=sc.nextDouble();
        
        System.out.println("Enter the end simulation time :");
		double time_end=sc.nextDouble();
		
		s.initialize();
       
		while(Method_Class.sim_time <= time_end)
		{
			s.timing();
			
	        switch(Method_Class.next_event_type)
			{
			case 1:
				s.arriveserver(tbegininspect, tendinspect,1);
				s.update_time_avg_stats(1);
				break;
				
			case 2:
			
				{
					s.departserver(tbegininspect, tendinspect,1);
					s.update_time_avg_stats(1);
			        
					if(ran.nextDouble() <= 0.7)
					{
						s.arriveserver(tbegin2ndservice, tend2ndservice,2);
					
						s.update_time_avg_stats(2);
						

					}
				}
				break;
				
			case 3:
			{
				s.departserver(tbegin2ndservice, tend2ndservice,2);
				s.update_time_avg_stats(2);
		       
				if(ran.nextDouble() <= 0.5)
				{
				
				
					s.arriveserver(tbegin3rdservice,tend3rdservice,3);
					s.update_time_avg_stats(3);
				}
			}
			break;
				
				default:
					s.departserver(tbegin3rdservice, tend3rdservice,3);
					s.update_time_avg_stats(3);
					break;
			
			}
			
			
}
		
		 while(Method_Class.num_in_q != 0 || Method_Class.num_in_q_2nd!= 0||Method_Class.num_in_q_3rd!=0)
	       {

			 s.timing();
	    	   switch (Method_Class.next_event_type)
	    	   
	    	   {

	                   case 1:break;

	                   case 2: s.departserver(tbegininspect, tendinspect,1);
	                           s.update_time_avg_stats(1);
	                           
	                           if(ran.nextDouble() <= 0.7)
	                           {
	                           s.arriveserver(tbegin2ndservice, tend2ndservice,2);
	                           s.update_time_avg_stats(2);
	                           }
	    	                   break ;
	                   case 3:
		                	   s.departserver(tbegin2ndservice, tend2ndservice,2);
	                           s.update_time_avg_stats(2);
	                           
	                           if(ran.nextDouble() <= 0.5)
	                           {
	                           s.arriveserver(tbegin3rdservice, tend3rdservice,3);
	                           s.update_time_avg_stats(3);
	                           }
	    	                   break ; 

	                   case 4: s.departserver(tbegin3rdservice, tend3rdservice,3);
	                           s.update_time_avg_stats(3);
	                           break;
	            }
	       }
		
		System.out.println("Simulation Result for FirstServer.........................");
		s.report();	
		
        System.out.println("Simulation Result for SecondServer................................");
		s.report_2nd_server();
		System.out.println("Simulation Result for ThirdServer................................");
	    s.report_3rd_server();
	    
		sc.close();
		

	

		

	}
	
	public static double expon(double  mean)
	{
		Random ran=new Random(1000);
	   return -mean * Math.log(ran.nextDouble());
	}
	 
	 
	 public static double uniform(double a, double b)
		{
			Random ran = new Random(1000);
			return (double) (a+((b-a)*ran.nextDouble()));
		}

}
