package top.hoyouly.framework.mv;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import top.hoyouly.framework.R;
import top.hoyouly.framework.bean.Student;
import top.hoyouly.framework.databinding.ActivityStudentBinding;

/**
 * Created by hoyouly on 18-3-29.
 */

//public class StudentActivity extends BaseBindingActivity<ActivityStudentBinding> {
public class StudentActivity extends Activity {
	private ActivityStudentBinding mBinding;
	private Student student;

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		mBinding=DataBindingUtil.setContentView(StudentActivity.this,R.layout.activity_student);
		initView();
	}

	//	@Override
	protected void initView() {
		student=new Student();
		student.setName("何磊");
		student.setNickName("hoyouly");
		mBinding.setStudent(student);

		mBinding.setController(new Controller());
	}

//	@Override
	protected int getLayouId() {
		return R.layout.activity_student;
	}

	public class Controller{
		public void onTextChanged(CharSequence s, int start, int before, int count){
			student.setName(s.toString());
			mBinding.setStudent(student);
		}
		public void onClick(View view){
			Toast.makeText(StudentActivity.this,"clickMe",Toast.LENGTH_SHORT).show();
		}

	}

}
