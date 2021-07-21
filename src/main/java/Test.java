import org.springframework.beans.factory.annotation.Autowired;

import com.banking.service.UtilityService;

public class Test {
	@Autowired
	public static UtilityService u= new UtilityService();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		String title = u.getCategoryOption(1).getTitle();
		System.out.print(title);
		
	}

}
