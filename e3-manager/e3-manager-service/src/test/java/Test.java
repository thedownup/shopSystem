//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.e3mall.common.pojo.EasyUITreeNode;
//import com.e3mall.mapper.TbItemCatMapper;
//import com.e3mall.pojo.TbItemCat;
//import com.e3mall.pojo.TbItemCatExample;
//import com.e3mall.pojo.TbItemCatExample.Criteria;
//public class Test {
//
//	@Autowired
//	private TbItemCatMapper tbItemCatMapper;
//
//	
//	@org.junit.Test
//	public void test() {
//		TbItemCatExample tbItemCatExample = new TbItemCatExample();
//		Criteria criteria = tbItemCatExample.createCriteria();
//		
//		criteria.andParentIdEqualTo(new Long(100));
//		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(tbItemCatExample);
//		List<EasyUITreeNode> resultList = new ArrayList<>();
//		for (TbItemCat tbItemCat : selectByExample) {
//			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
//			easyUITreeNode.setId(tbItemCat.getId());
//			easyUITreeNode.setText(tbItemCat.getName());
//			easyUITreeNode.setState(tbItemCat.getIsParent()?"closed":"open");
//			resultList.add(easyUITreeNode);
//		}
//	}
//
//}
