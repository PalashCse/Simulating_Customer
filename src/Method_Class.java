import java.util.Random;

public class Method_Class {
	
	Random ran=new Random();
	public static int Q_LIMIT=10000;
	public int IDLE=0;
	public static int BUSY=1;
    public static int server_no;
	public static double[] time_next_event=new double[5];
	
	//variable 1st server
	public int  num_events=4;
	public static  int   next_event_type, num_custs_delayed,num_in_q, server_status, num_delays_required;
	public static double area_num_in_q, area_server_status, sim_time, time_last_event, total_of_delays, mean_interarrival, mean_service;
	
	public static double[] time_arrival=new double[Q_LIMIT + 1];
	public static double[]time_depart=new double[Q_LIMIT+1];
	
	
	
	//variable 2nd server
	public static  double num_in_q_2nd,time_last_event_2nd,server_status_2nd,num_custs_delayed_2nd, total_of_delays_2nd,area_server_status_2nd, area_num_in_q_2nd;
	
	public static double[] time_arrival_2nd=new double[Q_LIMIT + 1];
	
	//variable 3rd server
	public static double num_in_q_3rd,time_last_event_3rd,server_status_3rd,num_custs_delayed_3rd, total_of_delays_3rd,area_server_status_3rd, area_num_in_q_3rd;

	public static double[] time_arrival_3rd=new double[Q_LIMIT + 1];
	
	public  void initialize()
	{
         sim_time =  0;
		
		server_status = 0;
		num_in_q = 0;
		time_last_event = 0;
		
		num_custs_delayed = 0;
		total_of_delays = 0.0;
		area_num_in_q =  0.0;
		area_server_status =0.0;
		
		time_next_event[1] = sim_time + Main.expon(mean_interarrival);
		time_next_event[2] = (double) 1.0e+30;
		time_next_event[3] = (double) 1.0e+30;
		time_next_event[4]= (double) 1.0e+30;
		
	}
	public  void timing()
    {
        int   i;
        double min_time_next_event = 1.0e+29;

        next_event_type = 0;

        for (i = 1; i <=  num_events; ++i)
        {
            if ( time_next_event[i] < min_time_next_event)
            {
                min_time_next_event =  time_next_event[i];
                next_event_type     = i;
            }
        }


         if ( next_event_type == 0)
         {
            System.out.println("\nEvent list empty at time"+  sim_time);
         }

         sim_time =  min_time_next_event;
         
    }
	
	
	public void arriveserver(double a, double b,int server_no)
    {
		double delay;
		
	  if(server_no==1)
	  {
       
        time_next_event[1] = sim_time + Main.expon(mean_interarrival);

        if (server_status == 1)
        {
            ++num_in_q;
            if(num_in_q > Q_LIMIT)
			{
				System.out.println("overflow of the array for time_arrival ");
				System.out.println("Time " + sim_time);
				System.exit(0);
			}
			
            time_arrival[num_in_q] = sim_time;
        }

        else
        {
        	delay=0;
            
            total_of_delays += delay;

            ++num_custs_delayed;
            server_status = 1;

          
            time_next_event[2] = sim_time + Main.uniform(a, b);
        }
        
	  }
	
	 else if(server_no==2)
	  {
		 if(server_status == BUSY)
			{
				++(num_in_q_2nd);
				
				if(num_in_q_2nd > Q_LIMIT)
				{
					System.out.println("overflow of the array time_arrival at ");
					System.out.println("time " + sim_time);
					System.exit(0);
				}
				
				time_arrival_2nd[(int) num_in_q_2nd] = sim_time;		
			}
			
			else
			{
				delay=0;
				total_of_delays_2nd += delay;
				
				++num_custs_delayed_2nd;
				server_status_2nd = BUSY;
				
				time_next_event[3] = sim_time + Main.uniform(a, b);
			}
		}
	 else
	 {
		 if(server_status == BUSY)
			{
				++(num_in_q_3rd);
				
				if(num_in_q_3rd > Q_LIMIT)
				{
					System.out.println("overflow of the array time_arrival at ");
					System.out.println("time " + sim_time);
					System.exit(0);
				}
				
				time_arrival_3rd[(int) num_in_q_3rd] = sim_time;		
			}
			
			else
			{
				delay=0;
				total_of_delays_3rd += delay;
				
				++num_custs_delayed_3rd;
				server_status_3rd= BUSY;
				
				time_next_event[4] = sim_time + Main.uniform(a, b);
			}

		 
	    }
	
	}
		
			
			public  void departserver(double a, double b,int server_no)
		    {
				double delay;
				int   i;
				
			if(server_no==1)
			{
			
		        
		      

		        if ( num_in_q == 0)
		        {
		        	 server_status = 0;
		        	 time_next_event[2] = 1.0e+30;
		        }

		        else
		        {
		        
		            -- num_in_q;
                    delay=  sim_time -  time_arrival[1];
		            total_of_delays += delay;

		            ++num_custs_delayed ;
		            time_next_event[2] =  sim_time + Main.uniform(a, b);

		            for (i = 1; i <= num_in_q; ++i)
		            	  time_arrival[i] =  time_arrival[i + 1];
		        }
		        
			}
			
			else if(server_no==2)
			{
				
				if(num_in_q_2nd == 0)
				{
					server_status_2nd = IDLE;
					time_next_event[3] = (double)1.0e+30;
				}
				
				else
				{
					--num_in_q_2nd;
					delay = sim_time - time_arrival_2nd[1];
					total_of_delays_2nd += delay;
					++num_custs_delayed_2nd;
					time_next_event[3] = sim_time + Main.uniform(a, b);
					
					for(i = 1; i <= num_in_q_2nd; ++i)
						time_arrival_2nd[i] = time_arrival_2nd[i+1];
				}

				
			}
			
			else
			{
				
				if(num_in_q_3rd == 0)
				{
					server_status_3rd = IDLE;
					time_next_event[4] = (double)1.0e+30;
				}
				
				else
				{
					--num_in_q_3rd;
					delay = sim_time - time_arrival_3rd[1];
					total_of_delays_3rd+= delay;
					++num_custs_delayed_3rd;
					time_next_event[4] = sim_time + Main.uniform(a, b);
					
					for(i = 1; i <= num_in_q_3rd; ++i)
						time_arrival_3rd[i] = time_arrival_3rd[i+1];
				}
				

				
			}
     }
			
			 
				
								
	    
			 public void update_time_avg_stats(int server_no)
			 {
				 if(server_no==1)
				 {
			    	double time_since_last_event;
			    	
					time_since_last_event = sim_time - time_last_event;
					time_last_event = sim_time;
					area_num_in_q += num_in_q*time_since_last_event;
					area_server_status += server_status*time_since_last_event;
				 }
				 
				 else if(server_no==2)
				 {
					    double time_since_last_event;
						
						time_since_last_event = sim_time - time_last_event_2nd;
						time_last_event_2nd = (int) sim_time;
						
						area_num_in_q_2nd += num_in_q_2nd*time_since_last_event;
						
						area_server_status_2nd += server_status_2nd*time_since_last_event;

					 
				 }
				 
				 else
				 {
					 double time_since_last_event;
						
						time_since_last_event = sim_time - time_last_event_3rd;
						time_last_event_3rd = (int) sim_time;
						
						area_num_in_q_3rd += num_in_q_3rd*time_since_last_event;
						
						area_server_status_3rd += server_status_3rd*time_since_last_event;
			
				 }
			 }
			 
			 
				
		
				
			 public void report()
			 
			    {
				 
				 
			    	System.out.println("Number of Customer    :    " + num_custs_delayed);
			    	
			    	System.out.println("Average delay in queue    :    " + total_of_delays/num_custs_delayed + " minutes ");
					System.out.println("Average number in queue    :    " + area_num_in_q/sim_time);
					System.out.println("Server utilization    :    " + area_server_status/sim_time);
					System.out.println("Time that simulation ended    :    " + sim_time + " minutes ");
					
					System.out.println();
			    }
			 
			   public void report_2nd_server()
			   
			   {
				 System.out.println("Number of Customer    :    " + num_custs_delayed_2nd);
			    	
			    	System.out.println("Average delay in queue    :    " + total_of_delays_2nd/num_custs_delayed_2nd + " minutes ");
					System.out.println("Average number in queue    :    " + area_num_in_q_2nd/sim_time);
					System.out.println("Server utilization    :    " + area_server_status_2nd/sim_time);
					System.out.println("Time that simulation ended    :    " + sim_time + " minutes ");
					
					System.out.println();
					
					
				 
			 }
			   public void report_3rd_server()
			   {
				   System.out.println("Number of Customer    :    " + num_custs_delayed_3rd);
			    	
			    	System.out.println("Average delay in queue    :    " + total_of_delays_3rd/num_custs_delayed_3rd + " minutes ");
					System.out.println("Average number in queue    :    " + area_num_in_q_3rd/sim_time);
					System.out.println("Server utilization    :    " + area_server_status_3rd/sim_time);
					System.out.println("Time that simulation ended    :    " + sim_time + " minutes ");
					
					System.out.println(); 

				   
			   }
				
				 
				
				 
					 			 
			        
			    
			
			 
			

	
}
