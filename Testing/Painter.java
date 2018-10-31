package Testing;

import Framework.Gui.GridWindow;
import Framework.Interfaces.KeyResponse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Framework.Util.*;

public class Painter extends GridWindow {
    public Painter(String title, int xDim, int yDim, int scaleFactor) {
        super(title, xDim, yDim, scaleFactor);
    }
    short ToShort(){
        short out=0;
        for (int i = 0; i < length; i++) {
            if(IsSet(i)){
                out+=1<<i;
            }
        }
        return out;
    }
    void SetShort(short in,int cOn,int cOff){
        for (int i = 0; i < length; i++) {
            if((in>>i&1)==0){
                SetPix(i,cOff);
            }
            else{
                SetPix(i,cOn);
            }
        }
    }

    public boolean IsSet(int i){
        return GetRed(this.GetPix(i))!=0;
    }
    public static void main(String[] args) {
        Painter win=new Painter("painter",5,7,100);
        win.AddMouseListeners(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(win.ClickXsq(e)+"");
                int i=win.I(win.ClickXsq(e),win.ClickYsq(e));
                if(win.IsSet(i)){
                    win.SetPix(i,BLACK);
                }
                else{
                    win.SetPix(i,RED);
                }
            }
        });
        win.AddKeyResponses(new KeyResponse() {
            @Override
            public void Response(char c, int keyCode) {
                win.SetChar(c,1,5,GREEN,BLUE);
            }
        },null);
            //public void RunEvent(KeyEvent e) {
            //    if(e.getID()==KeyEvent.KEY_RELEASED){
            //        switch (e.getKeyChar()){
            //            case 'c':win.Clear(BLACK);break;
            //            case 's': System.out.println(win.ToShort());break;
            //        }
            //    }
            //}
    }
}
