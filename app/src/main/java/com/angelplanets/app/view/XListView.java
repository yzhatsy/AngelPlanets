package com.angelplanets.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.angelplanets.app.R;

import org.xutils.common.util.DensityUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * 改编自XListView
 * <p/>
 * 这个XListView继承自RelativeLayout，导致一些ListView的属性没有，如果大家项目中需要用到ListView的一些属性，
 * 请在本类中加入
 *
 */
@SuppressWarnings("unused")
public class XListView extends RelativeLayout implements OnScrollListener {

    private ListView mListView;
    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private IXListViewListener mListViewListener;

    // -- header view
    private XListViewHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh;
    private boolean mPullRefreshing; // is refreashing.

    // -- footer view
    public XListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady;

    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    // feature.

    private boolean mIsAnimation; //默认为false
    private Animation animationDown; //向上动画
    private Animation animationUp; //下拉动画
    private TextView infoHint; //弹出的那个textview
    private String count = "0"; //表示更新了多少条
    private Context context;

    public XListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    @SuppressLint("InflateParams")
    private void initWithContext(Context context) {
        this.context = context;
        initAnimation();
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        mListView = (ListView) LayoutInflater.from(context).inflate(R.layout.view_xlistview, null);
        OnTouchListener touch = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                if (mLastY == -1) {
                    mLastY = ev.getRawY();
                }
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastY = ev.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        final float deltaY = ev.getRawY() - mLastY;
                        mLastY = ev.getRawY();
                        if (mListView.getFirstVisiblePosition() == 0
                                && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                            // the first item is showing, header has shown or pull down.
                            updateHeaderHeight(deltaY / OFFSET_RADIO);
                            invokeOnScrolling();
                        } else if (mListView.getLastVisiblePosition() == mTotalItemCount - 1
                                && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                            // last item, already pulled up or want to pull up.
                            updateFooterHeight(-deltaY / OFFSET_RADIO);
                        }
                        break;
                    default:
                        mLastY = -1; // reset
                        if (mListView.getFirstVisiblePosition() == 0) {
                            // invoke refresh
                            if (mEnablePullRefresh
                                    && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                                mPullRefreshing = true;
                                mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                                if (mListViewListener != null) {
                                    mListViewListener.onRefresh();
                                }
                            }
                            resetHeaderHeight();
                        }
                        if (mListView.getLastVisiblePosition() == mTotalItemCount - 1) {
                            // invoke load more.
                            if (mEnablePullLoad
                                    && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                                startLoadMore();
                            }
                            resetFooterHeight();
                        }
                        break;
                }
                return false;
            }
        };
        mListView.setOnTouchListener(touch);
        mListView.setOnScrollListener(this);
        addView(mListView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));

        // init header view
        mHeaderView = new XListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
        mListView.addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new XListViewFooter(context);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderViewHeight = mHeaderViewContent.getHeight();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        infoHint = (TextView) LayoutInflater.from(context).inflate(R.layout.view_xlistview_infohint, null);
        LayoutParams para = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        para.setMargins(5, 0, 5, 0);
        infoHint.setLayoutParams(para);
        infoHint.setVisibility(View.INVISIBLE);
        addView(infoHint);
    }

    private void initAnimation() {
        animationUp = new TranslateAnimation(RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF,
                0.1f, RELATIVE_TO_SELF, -1);
        animationUp.setDuration(1000);
        animationUp.setRepeatCount(0);
        animationUp.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                infoHint.setVisibility(View.INVISIBLE);
            }
        });

        animationDown = new TranslateAnimation(RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0,
                RELATIVE_TO_SELF, -1, RELATIVE_TO_SELF, 0.1f);
        animationDown.setDuration(1000);
        animationDown.setRepeatCount(0);
        animationDown.setFillAfter(true);
        animationDown.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                infoHint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        infoHint.startAnimation(animationUp);
                    }
                }, 1000);
            }
        });

    }

    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (!mIsFooterReady) {
            mIsFooterReady = true;
            mListView.addFooterView(mFooterView);
        }
        mListView.setAdapter(adapter);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListView.setOnItemClickListener(listener);
    }

    public void removeHeaderView(View v) {
        mListView.removeHeaderView(v);
    }

    public void addHeaderView(View v) {
        mListView.removeHeaderView(v);
        mListView.addHeaderView(v);
    }

    /**
     * enable or disable pull down refresh feature.
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     */
    public void setPullLoadEnable(boolean enable, String str) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide(str);
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing) {
            mPullRefreshing = false;
            if (mIsAnimation) {
                infoHint.setText(String.format("更新了%s条数据", count));
                infoHint.startAnimation(animationDown);
            }
            resetHeaderHeight();
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText(time);
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(XListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            }
        }
        mListView.setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);
        // setSelection(mTotalItemCount - 1); // scroll to bottom
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    public void setXListViewListener(IXListViewListener l) {
        mListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface IXListViewListener {
        void onRefresh();

        void onLoadMore();
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void onFresh() {
        mPullRefreshing = true;
        mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
        if (mListViewListener != null) {
            mListViewListener.onRefresh();
        }
        resetHeaderHeight();
        int finalHeight = DensityUtil.dip2px(60);  //XListViewHeader中的高度

        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, 0, 0, finalHeight,
                SCROLL_DURATION);
        invalidate();
    }

    public void setIsAnimation(boolean isAnimation) {
        mIsAnimation = isAnimation;
    }
}
