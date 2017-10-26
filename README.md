# ExpandView
展开控件，点击更多展开
<br/>
<br/>
## 效果演示图<br/>
![DragGridView](https://raw.githubusercontent.com/wkp111/ExpandView/master/app/src/main/res/drawable/expand01.gif "演示图1")
<br/>
![DragGridView](https://raw.githubusercontent.com/wkp111/ExpandView/master/app/src/main/res/drawable/expand02.gif "演示图2")
<br/>
<br/>
## Gradle集成<br>
compile 'com.wkp:ExpandView:1.0.1'
<br>
<br>
## 使用详解<br/>
> 属性讲解<br/>
`

        <!--每行字段数-->
        <attr name="wkp_column" format="integer"/>
        <!--最少显示行数-->
        <attr name="wkp_rowMin" format="integer"/>
        <!--条目间距-->
        <attr name="wkp_space" format="dimension"/>
        <!--条目动画时长-->
        <attr name="wkp_itemDuration" format="integer"/>
        <!--条目高度-->
        <attr name="wkp_itemHeight" format="dimension"/>
        <!--“更多”按钮图片-->
        <attr name="wkp_moreButtonImg" format="reference"/>
        <!--“更多”按钮文本-->
        <attr name="wkp_moreButtonText" format="string"/>
        <!--显示文本模式时的条目背景色-->
        <attr name="wkp_textBgColor" format="color"/>
        <!--显示文本模式时的条目文本颜色-->
        <attr name="wkp_textColor" format="color"/>
        <!--显示文本模式时的文本大小-->
        <attr name="wkp_textSize" format="dimension"/>
        <!--显示文本模式时的条目背景图-->
        <attr name="wkp_textBgRes" format="reference"/>
`
<br/>
> 布局<br/>
`

    图1布局
    <com.wkp.expandview_lib.view.ExpandView
        app:wkp_textSize="@dimen/size_16sp"
        app:wkp_column="3"
        app:wkp_rowMin="3"
        app:wkp_itemHeight="120dp"
        app:wkp_textBgRes="@drawable/text_bg"
        android:id="@+id/ev"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </com.wkp.expandview_lib.view.ExpandView>


    图2布局
    <com.wkp.expandview_lib.view.ExpandView
        app:wkp_textSize="@dimen/size_16sp"
        app:wkp_column="4"
        app:wkp_rowMin="2"
        app:wkp_itemHeight="120dp"
        app:wkp_textBgRes="@drawable/text_bg"
        android:id="@+id/ev"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        
    </com.wkp.expandview_lib.view.ExpandView>
`
<br/>
> 代码示例<br/>
`

public class MainActivity extends AppCompatActivity {

    private static final String[] items = {"雨水滴在我的外套","已找到","每分每秒","来啊，互相伤害啊","等你到天涯海角","遇见了你才知道你对我多重要",
            "123","456","789","abc","def","收起"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ExpandView expandView = (ExpandView) findViewById(R.id.ev);
        //设置数据
        expandView.setTextItems(items);
        //条目点击监听
        expandView.setOnItemClickListener(new ExpandView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewGroup parent, int position) {
                if (position == items.length - 1) {
                    //收起隐藏条目
                    expandView.packUpItems();
                }
            }
        });
    }
    
`
<br/>
## 寄语<br/>
控件支持直接代码创建，还有更多API请观看<a href="https://github.com/wkp111/ExpandView/blob/master/expandview-lib/src/main/java/com/wkp/expandview_lib/view/ExpandView.java">ExpandView.java</a>内的注释说明。<br/>
欢迎大家使用，感觉好用请给个Star鼓励一下，谢谢！<br/>
大家如果有更好的意见或建议以及好的灵感，请邮箱作者，谢谢！<br/>
QQ邮箱：1535514884@qq.com<br/>
163邮箱：15889686524@163.com<br/>
Gmail邮箱：wkp15889686524@gmail.com
