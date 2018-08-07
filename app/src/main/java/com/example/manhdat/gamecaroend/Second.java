package com.example.manhdat.gamecaroend;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class Second extends AppCompatActivity {
    public static int LINE_SIZE;
    public static int PLAYER_SIZE;

    private View decorView;
    private char[][] board;
    private Bitmap screen, playerO, playerX, newPlayerO, newPlayerX, newTurnO, newTurnX;
    private final int[][] stateO={ {-1, -1}, {-1, -1} };
    private final int[][] stateX={ {-1, -1}, {-1, -1} };
    private char currentPlayer='O';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PLAYER_SIZE = 60;
        LINE_SIZE = 5;
        decorView = getWindow().getDecorView();
        DisplayMetrics realDisp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(realDisp);
        final int W = realDisp.widthPixels, H = realDisp.heightPixels;
        screen = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
        screen.eraseColor(Color.WHITE);
        // ve ban co
        final Canvas canvas=new Canvas(screen);
        Paint paint=new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.FILL);
        final int a=PLAYER_SIZE+LINE_SIZE;
        int numRow=(H-LINE_SIZE)/(a);
        int numCol=(W-LINE_SIZE)/a;
        int maxX=numCol*a+LINE_SIZE;
        int maxY=numRow*a+LINE_SIZE;
        for (int i=0, x=0; i<=numCol; ++i, x+=a)
            canvas.drawRect(x, 0, x+LINE_SIZE, maxY, paint);
        for (int i=0, y=0; i<=numRow; ++i, y+=a)
            canvas.drawRect(0, y, maxX, y+LINE_SIZE, paint);
        setContentView(R.layout.activity_second);

        ViewGroup container = (ViewGroup) findViewById(R.id.LL);

        final View v = new View(this) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                canvas.drawBitmap(screen, 0, 0, null);
            }
        };
        container.addView(v);

        // load hinh anh
        playerO=getImage(R.drawable.o);
        playerX=getImage(R.drawable.x);
        newPlayerO=getImage(R.drawable.o);
        newPlayerX=getImage(R.drawable.x);
        newTurnO=getImage(R.drawable.o);
        newTurnX=getImage(R.drawable.x);

        // tinh toan ma tran
        board=new char[numCol][numRow];
        v.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction()!=MotionEvent.ACTION_DOWN) return true;
                int x=((int)event.getX())/a;
                int y=((int)event.getY())/a;
                if (board[x][y]!='\0') return true;
                board[x][y]=currentPlayer;
                play(currentPlayer, x, y);
                currentPlayer=(currentPlayer=='O') ? 'X' : 'O';
                return true;
            }

            // ve hinh quan O , X va kiem tra Win
            private void play(char currentPlayer, int x, int y)
            {
                Bitmap player, newPlayer, newTurn;
                int state[][];
                if (currentPlayer=='O')
                {
                    player=playerO; newPlayer=newPlayerO; newTurn=newTurnO;
                    state=stateO;
                }
                else
                {
                    player=playerX; newPlayer=newPlayerX; newTurn=newTurnX;
                    state=stateX;
                }

                if (state[1][0]!=-1) canvas.drawBitmap(player, state[1][0]*a+LINE_SIZE, state[1][1]*a+LINE_SIZE, null);
                state[1][0]=state[0][0]; state[1][1]=state[0][1];
                if (state[1][0]!=-1) canvas.drawBitmap(newPlayer, state[1][0]*a+LINE_SIZE, state[1][1]*a+LINE_SIZE, null);
                state[0][0]=x; state[0][1]=y;
                canvas.drawBitmap(newTurn, x*a+LINE_SIZE, y*a+LINE_SIZE, null);
                if (KiemTra.isWin(board, x, y, currentPlayer))
                {

                    // Thong bao chien thang
                    AlertDialog.Builder builder=new AlertDialog.Builder(Second.this);
                    builder.setTitle("Ban thang roi !");
                    builder.setMessage(currentPlayer+" da thang !");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface p1, int p2)
                        {
                           Second.this.finish();
                        }
                    });
                    builder.show();
                }

                v.invalidate();
            }
        });

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
    private Bitmap getImage(int id)
    {
        Bitmap tmp= BitmapFactory.decodeResource(getResources(), id);
        tmp=Bitmap.createScaledBitmap(tmp, PLAYER_SIZE, PLAYER_SIZE, false);
        return tmp;
    }
}
