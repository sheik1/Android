package com.example.sheikr.muziekapplicatie.visualizer;

/**
 *  This is the sleeper for the visualizer
 *  This allows the sampler to collect data before running.
 *  @author Pontus Holmberg (EndLessMind)
 *  Email: the_mr_hb@hotmail.com
 **/

public class CSleeper
  implements Runnable
{
  private Boolean done = Boolean.valueOf(false);
  private VisualizerActivity m_ma;
  private CSampler m_sampler;

  public CSleeper(VisualizerActivity paramMainActivity, CSampler paramCSampler)
  {
    m_ma = paramMainActivity;
    m_sampler = paramCSampler;
  }

  public void run()
  {
    try {
		m_sampler.Init();
	} catch (Exception e) {
		return;
	}
    while (true)
      try
      {
        Thread.sleep(1000L);
        System.out.println("Tick");
        continue;
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
  }
}