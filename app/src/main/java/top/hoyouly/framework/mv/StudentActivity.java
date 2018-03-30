package top.hoyouly.framework.mv;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import top.hoyouly.framework.BR;
import top.hoyouly.framework.R;
import top.hoyouly.framework.adapter.BaseLvAdapter;
import top.hoyouly.framework.bean.BenefitBean;
import top.hoyouly.framework.bean.GankBean;
import top.hoyouly.framework.bean.Student;
import top.hoyouly.framework.databinding.ActivityStudentBinding;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.loder.GankLoader;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;

/**
 * Created by hoyouly on 18-3-29.
 */

public class StudentActivity extends BaseBindingActivity<ActivityStudentBinding> {
    private Student student;
    private List<BenefitBean> benefitBeans;

    private GankLoader loader;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            BaseLvAdapter<BenefitBean> adapter = new BaseLvAdapter<>(StudentActivity.this, R.layout.listview_item, benefitBeans, BR.benefit);//是BR中的，这个BR和我们项目中的R文件类似，都是系统自动生成的。
            mBinding.lv.setAdapter(adapter);
        }
    };
    @Override
    protected void initView() {
        initData();
        student = new Student();
        student.setName("何磊");
        student.age=77;
        student.setNickName("hoyouly");
        student.userface="http://img2.cache.netease.com/auto/2016/7/28/201607282215432cd8a.jpg";
        mBinding.setStudent(student);

        mBinding.setController(new Controller());

    }

    private void initData() {
        loader=new GankLoader();

    }

    private void getListData() {
        SubscriberOnNextListener<GankBean> onNextListener = new SubscriberOnNextListener<GankBean>() {
            @Override
            public void onNext(GankBean gankBean) {
                StudentActivity.this.benefitBeans=gankBean.results;
                mHandler.sendEmptyMessage(0);
            }
        };
        loader.getGankBenefitList(10,1).subscribe(new ProgressDialogSubscriber<GankBean>(onNextListener,StudentActivity.this));
    }

    @Override
    protected int getLayouId() {
        return R.layout.activity_student;
    }

    public class Controller {
        public void onChanged(CharSequence s, int start, int before, int count) {
            student.setName(s.toString());
            mBinding.setStudent(student);
        }

        public void onClicked(View view) {
            Toast.makeText(StudentActivity.this, "clickMe", Toast.LENGTH_SHORT).show();
            getListData();
        }

        public void onClickListener(Student student){
            Toast.makeText(StudentActivity.this, student.getNickName(), Toast.LENGTH_SHORT).show();
        }

    }

}
