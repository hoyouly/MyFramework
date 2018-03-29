package top.hoyouly.framework.mv;

import android.view.View;
import android.widget.Toast;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.Student;
import top.hoyouly.framework.databinding.ActivityStudentBinding;

/**
 * Created by hoyouly on 18-3-29.
 */

public class StudentActivity extends BaseBindingActivity<ActivityStudentBinding> {
    private Student student;

    @Override
    protected void initView() {
        student = new Student();
        student.setName("何磊");
        student.age=77;
        student.setNickName("hoyouly");
        student.userface="http://img2.cache.netease.com/auto/2016/7/28/201607282215432cd8a.jpg";
        mBinding.setStudent(student);

        mBinding.setController(new Controller());

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
        }

        public void onClickListener(Student student){
            Toast.makeText(StudentActivity.this, student.getNickName(), Toast.LENGTH_SHORT).show();

        }

    }

}
