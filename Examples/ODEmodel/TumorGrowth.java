package Examples.ODEmodel;

import Framework.Gui.PlotLine;
import Framework.Gui.PlotWindow;
import Framework.Tools.ODESolver;

import java.util.ArrayList;

import static Framework.Util.BLUE;
import static Framework.Util.GREEN;

public class TumorGrowth {
    public final double tumorGrowthRate;
    public final double immuneDeathRate;
    public final double immuneKillTumorRate;
    public final double immuneRecrutimentRate;

    public final static int TUMOR=0,IMMUNE=1;

    public TumorGrowth(double tumorGrowthRate, double immuneDeathRate, double immuneKillTumorRate, double immuneRecrutimentRate) {
        this.tumorGrowthRate = tumorGrowthRate;
        this.immuneDeathRate = immuneDeathRate;
        this.immuneKillTumorRate = immuneKillTumorRate;
        this.immuneRecrutimentRate = immuneRecrutimentRate;
    }

    public void PopulationDerivatives(double t,double[]pops,double[]deltas){
        //tumor update
        deltas[TUMOR]=tumorGrowthRate*pops[TUMOR]-immuneKillTumorRate*pops[TUMOR]*pops[IMMUNE];
        deltas[IMMUNE]=-immuneDeathRate*pops[IMMUNE]+immuneRecrutimentRate*pops[TUMOR]*pops[IMMUNE];
    }

    public static void main(String[] args) {
        TumorGrowth model=new TumorGrowth(Math.sqrt(2),0.02,0.001,0.001);
        double[]startPops=new double[]{1000,10};
        ArrayList<double[]> states=new ArrayList<>();
        states.add(startPops);
        ArrayList<Double> ts=new ArrayList<>();
        ts.add(0.0);
        PlotWindow win=new PlotWindow(250,250,2);
        PlotLine tumorLine=new PlotLine(win,GREEN);
        PlotLine immuneLine=new PlotLine(win,BLUE);
        ODESolver solver=new ODESolver(model::PopulationDerivatives);
        double dtStart=0.001;
        double errorTol=0.001;
        solver.Runge45(states,ts,10,dtStart,errorTol,0);
        for (int i = 0; i < states.size(); i++) {
            tumorLine.AddSegment(ts.get(i),states.get(i)[0]);
            immuneLine.AddSegment(ts.get(i),states.get(i)[1]);
        }
    }
}
