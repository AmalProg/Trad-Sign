package com.enib.lesbg.tradsign;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.MotionEvent;

public class AnimatedCharacter extends View {
    public static int NBR_POINTS = 16;

    private PointF m_Points[] = new PointF[NBR_POINTS];
    private PointF m_BasePoints[] = new PointF[NBR_POINTS]; // points to begin the animation
    private PointF m_NextPoints[] = new PointF[NBR_POINTS]; // points to end the animation
    private Map<String, Vector2i> m_BodyParts = new HashMap<>();
    private int m_NbrPositions = 0;
    private int m_ActualPosition = 1;
    private String m_ActualWord = "";

    private float m_ElapsedTimeAnimStart = 0; // time elapsed since last animation started
    private float m_BaseAnimationTime = 3000; // animation time in ms
    private float m_AnimationTime = 0; // animation time in ms
    private int m_FrameRates = 10;
    private boolean m_AnimationEnd = true;
    private Drawable m_Refresh = getResources().getDrawable(R.drawable.refresh);

    private Paint m_BodyPaint = new Paint(); // drawing tool
    private Paint m_HeadPaint = new Paint(); // drawing tool

    private AnimDao animDAO = new AnimDao(getContext());

    public AnimatedCharacter(Context context, AttributeSet attrSet) {
        super(context, attrSet);

        animDAO.open();

        for(int i = 0; i < m_Points.length; i++) { // points initialization
            m_Points[i] = new PointF(0, 0);
            m_BasePoints[i] = new PointF(0, 0);
            m_NextPoints[i] = new PointF(0, 0);
        }

        m_BodyPaint.setARGB(255, 0, 0, 0);
        m_BodyPaint.setStyle(Paint.Style.STROKE);
        m_BodyPaint.setStrokeWidth(6);
        m_HeadPaint.setARGB(255, 238, 213, 183);
        m_HeadPaint.setStyle(Paint.Style.STROKE);
        m_HeadPaint.setStrokeWidth(5);

        m_BodyParts.put("armD", new Vector2i(m_Points[0], m_Points[1]));
        m_BodyParts.put("forearmD", new Vector2i(m_Points[1], m_Points[2]));
        m_BodyParts.put("thumbD", new Vector2i(m_Points[2], m_Points[3]));
        m_BodyParts.put("forefingerD", new Vector2i(m_Points[2], m_Points[4]));
        m_BodyParts.put("middleFingerD", new Vector2i(m_Points[2], m_Points[5]));
        m_BodyParts.put("ringFingerD", new Vector2i(m_Points[2], m_Points[6]));
        m_BodyParts.put("littleFingerD", new Vector2i(m_Points[2], m_Points[7]));

        m_BodyParts.put("armG", new Vector2i(m_Points[8], m_Points[9]));
        m_BodyParts.put("forearmG", new Vector2i(m_Points[9], m_Points[10]));
        m_BodyParts.put("thumbG", new Vector2i(m_Points[10], m_Points[11]));
        m_BodyParts.put("forefingerG", new Vector2i(m_Points[10], m_Points[12]));
        m_BodyParts.put("middleFingerG", new Vector2i(m_Points[10], m_Points[13]));
        m_BodyParts.put("ringFingerG", new Vector2i(m_Points[10], m_Points[14]));
        m_BodyParts.put("littleFingerG", new Vector2i(m_Points[10], m_Points[15]));
    }

    public void onDestroy() {
        animDAO.close();
    }

    public void prepareNewAnim(String mot) {
        m_NbrPositions = animDAO.getPositionsNumber(mot);

        if(m_NbrPositions > 0) {
            m_ActualWord = mot;
            m_ActualPosition = 1;
            m_AnimationTime = m_BaseAnimationTime / m_NbrPositions-1;
            PointF points[] = animDAO.getPoints(mot, m_ActualPosition-1);
            PointF nextPoints[] = animDAO.getPoints(mot, m_ActualPosition);
            if(points != null && nextPoints != null)
                newAnim(points, nextPoints);
        }
        else {
            for(int i = 0; i < m_Points.length; i++) { // points initialization
                m_Points[i].set(0, 0);
                m_BasePoints[i].set(0, 0);
                m_NextPoints[i].set(0, 0);

                m_ActualPosition = 0;
                m_NbrPositions = 1;
                m_AnimationEnd = false;
                m_ElapsedTimeAnimStart = 0;
                m_AnimationTime = 0;
            }
        }
    }

    public void newAnim(PointF[] points, PointF[] nextPoints) {
        for(int i = 0; i < points.length; i++) {
            m_BasePoints[i] = new PointF(points[i].x, points[i].y);
            m_NextPoints[i] = new PointF(nextPoints[i].x, nextPoints[i].y);
        }

        m_AnimationEnd = false;
        m_ElapsedTimeAnimStart = 0;
    }

    public void setAnimationTime(int animT) {
        m_BaseAnimationTime = animT;
    }

    public void setFrameRates(int frameRates) {
        m_FrameRates = frameRates;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!m_AnimationEnd) {
            anim(m_FrameRates);
        }
        else if(m_ActualPosition < m_NbrPositions-1) {
            m_ActualPosition++;
            m_AnimationEnd = (m_ActualPosition == m_NbrPositions-1);

            PointF points[] = animDAO.getPoints(m_ActualWord, m_ActualPosition-1);
            PointF nextPoints[] = animDAO.getPoints(m_ActualWord, m_ActualPosition);
            if(points != null && nextPoints != null)
                newAnim(points, nextPoints);
        }

        float canH = canvas.getHeight();
        float canW = canvas.getWidth();

        // not moving parts are drawn first
        canvas.drawLine(canW/2, canH*2/7, canW/2, canH*6/7, m_BodyPaint);
        canvas.drawCircle(canW/2, canH*(2/7.f-1/10.f), canH/10, m_HeadPaint);

        // moving parts are drawn there
        for(Map.Entry<String, Vector2i> entry : m_BodyParts.entrySet()) {
            canvas.drawLine(entry.getValue().p1.x * canW, entry.getValue().p1.y * canH,
                    entry.getValue().p2.x * canW, entry.getValue().p2.y * canH, m_BodyPaint); // draw a line for each body part
        }

        // refresh icon appear at the end of animation
        if(m_AnimationEnd && m_ActualPosition == m_NbrPositions-1) {
            m_Refresh.setBounds((int)canW*4/10, (int)canH*4/10, (int)canW*6/10, (int)canH*6/10);
            m_Refresh.draw(canvas);
        }

        postInvalidateDelayed(m_FrameRates); // recall draw each 10ms approx
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(m_AnimationEnd) {
            prepareNewAnim(m_ActualWord);
        }

        return true;
    }

    private void anim(int elapsedTime) {
        m_ElapsedTimeAnimStart += elapsedTime;
        if(m_ElapsedTimeAnimStart > m_AnimationTime) {
            m_ElapsedTimeAnimStart = m_AnimationTime;
            m_AnimationEnd = true;
        }

        for(int i = 0; i < m_Points.length; i++) {
            m_Points[i].x = m_BasePoints[i].x + (m_NextPoints[i].x - m_BasePoints[i].x) * m_ElapsedTimeAnimStart / m_AnimationTime;
            m_Points[i].y = m_BasePoints[i].y + (m_NextPoints[i].y - m_BasePoints[i].y) * m_ElapsedTimeAnimStart / m_AnimationTime;
        }
    }
}
