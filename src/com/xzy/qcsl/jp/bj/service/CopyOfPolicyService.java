package com.xzy.qcsl.jp.bj.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;









import com.xzy.qcsl.jp.bj.model.ChangePriceLog;
import com.xzy.qcsl.jp.bj.model.PolicyFloor;
import com.xzy.qcsl.jp.bj.model.PolicyFloorLog;
import com.xzy.qcsl.jp.bj.model.User;
import com.xzy.qcsl.jp.bj.model.YPrice;
import com.xzy.qcsl.jp.bj.util.ConnectionUtil;
import com.xzy.qcsl.jp.bj.util.StatementUtil;
import com.xzy.qcsl.jp.bj.util.StringUtil;

/**
 * @author xiangzy
 * @date 2015-6-27
 * 
 */
public class CopyOfPolicyService {

	private static CopyOfPolicyService policyService;
	
	private static Random random = new Random();

	private Connection connection;

	public CopyOfPolicyService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = this.newConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static CopyOfPolicyService getInstance() {
		if (policyService == null) {
			policyService = new CopyOfPolicyService();
		}
		return policyService;
	}

	public Connection newConnection() throws Exception {
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/qcsl_bj?user=root&password=root");
		return conn;
	}

	public Connection getConnection() {
		return this.connection;
	}

	/**
	 * 保存政策底价
	 * @throws Exception 
	 */
	public int savePolicyFloor(PolicyFloor p) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
		    conn = newConnection();
			/**
				id varchar(50) primary key,
			  	policy_id varchar(50),
			  	floor_price int,
			  	need_set_cpa boolean,
				need_set_cpc boolean,
				is_price_changed boolean,
				under int,
				status boolean
			 */
		    if(p.getId()==null ||p.getId().trim().equals("")){
		    	stmt = conn.prepareStatement("insert into policy_floor values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		    	p.setId(System.currentTimeMillis()+""+random.nextInt());
		    	stmt.setString(1,p.getId());
				stmt.setString(2, p.getPolicyId());
				stmt.setInt(3, p.getFloorPrice());
				stmt.setBoolean(4, p.isNeedSetCPA());
				stmt.setBoolean(5, p.isNeedSetCPC());
				stmt.setBoolean(6, p.isPriceChanged());
				stmt.setInt(7, p.getUnder());
				stmt.setBoolean(8, p.isStatus());
				stmt.setString(9, p.getLastUsername());
				stmt.setString(10, p.getPtype());
				stmt.setInt(11, p.getFloorPoint());
				stmt.setInt(12, p.getType());
				stmt.setBoolean(13, p.isCompareCabin());
				stmt.setInt(14, p.getCpaDcpc());
				
		    }else{
		    	stmt = conn.prepareStatement("UPDATE policy_floor " +
		    			"SET policy_id=?,floor_price=?, need_set_cpa=?,need_set_cpc=?,under=?,status=?,last_user_name=?,floor_point=?,type=?,compare_cabin=?,cpa_d_cpc=?  where id=?");
		    	stmt.setString(1, p.getPolicyId());
		    	stmt.setInt(2, p.getFloorPrice());
		    	stmt.setBoolean(3, p.isNeedSetCPA());
				stmt.setBoolean(4, p.isNeedSetCPC());
				stmt.setInt(5, p.getUnder());
				stmt.setBoolean(6, p.isStatus());
				stmt.setString(7, p.getLastUsername());
				stmt.setInt(8, p.getFloorPoint());
				stmt.setInt(9, p.getType());
				stmt.setBoolean(10, p.isCompareCabin());
				stmt.setInt(11, p.getCpaDcpc());
				stmt.setString(12,p.getId());
		    
		    }
			
			int rowCount = stmt.executeUpdate();
			if(rowCount==0){
				System.out.println("更新记录0条");
			}
			return rowCount;
			
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
			
		}
		
		
		
	}

	public PolicyFloor getPolicyFloorByPolicyId(String policyId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PolicyFloor policyFloor=null;
		try{
			
		    conn = newConnection();
			stmt = conn.prepareStatement("select * from policy_floor where policy_id=?");
			stmt.setString(1, policyId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				/**
					id varchar(50) primary key,
				  	policy_id varchar(50),
				  	floor_price int,
				  	need_set_cpa boolean,
					need_set_cpc boolean,
					is_price_changed boolean,
					under int,
					status boolean
					floor_point int,
					type int
				 */
				policyFloor = new PolicyFloor();
				policyFloor.setPolicyId(policyId);
				policyFloor.setId(rs.getString("id"));
				policyFloor.setFloorPrice(rs.getInt("floor_price"));
				policyFloor.setNeedSetCPA(rs.getBoolean("need_set_cpa"));
				policyFloor.setNeedSetCPC(rs.getBoolean("need_set_cpc"));
				policyFloor.setStatus(rs.getBoolean("status"));
				policyFloor.setUnder(rs.getInt("under"));
				policyFloor.setLastUsername(rs.getString("last_user_name"));
				policyFloor.setFloorPoint(rs.getInt("floor_point"));
				policyFloor.setType(rs.getInt("type"));
				policyFloor.setCompareCabin(rs.getBoolean("compare_cabin"));
				policyFloor.setCpaDcpc(rs.getInt("cpa_d_cpc"));
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
		
		return policyFloor;

	}
	
	/**
	 * 取得所有该用户有效的底价
	 * @param username
	 * @return
	 * @throws Exception 
	 */
	public List<PolicyFloor> getPolicyFloorListByUser(String username) throws Exception{
		List<PolicyFloor> list = new ArrayList<PolicyFloor>();
		

		Connection conn = null;
		PreparedStatement stmt = null;
		PolicyFloor policyFloor=null;
		try{
			
		    conn = newConnection();
			stmt = conn.prepareStatement("select * from policy_floor where last_user_name=? and status=true");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				policyFloor = new PolicyFloor();
				policyFloor.setId(rs.getString("id"));
				policyFloor.setPolicyId(rs.getString("policy_id"));
				policyFloor.setFloorPrice(rs.getInt("floor_price"));
				policyFloor.setNeedSetCPA(rs.getBoolean("need_set_cpa"));
				policyFloor.setNeedSetCPC(rs.getBoolean("need_set_cpc"));
				policyFloor.setStatus(rs.getBoolean("status"));
				policyFloor.setUnder(rs.getInt("under"));
				policyFloor.setLastUsername(username);
				policyFloor.setPtype(rs.getString("ptype"));
				policyFloor.setFloorPoint(rs.getInt("floor_point"));
				policyFloor.setType(rs.getInt("type"));
				policyFloor.setCompareCabin(rs.getBoolean("compare_cabin"));
				policyFloor.setCpaDcpc(rs.getInt("cpa_d_cpc"));
				
				list.add(policyFloor);
		
			}
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
		
		return list;
	}

	public PolicyFloorLog makePolicyFloorLog(PolicyFloor policyFloor,User user){
		PolicyFloorLog policyFloorLog = new PolicyFloorLog();
		policyFloorLog.setPolicyFloorId(policyFloor.getId());
		policyFloorLog.setDate(new Timestamp(System.currentTimeMillis()));
		policyFloorLog.setUsername(user.getUsername());
		List<PolicyFloorLog> logList = new ArrayList<PolicyFloorLog>();
		logList = policyService.getPolicyFloorLogByPolicyFloorId(policyFloor.getId());
		//action; //0 新增， 1修改
		if(logList!=null && logList.size()!=0){
			PolicyFloorLog lastLog = logList.get(0);//最近一次设置的底价
			policyFloorLog.setAction(1);
			policyFloorLog.setOldValue(lastLog.getNewValue());
		}else{
			policyFloorLog.setAction(0);
			policyFloorLog.setOldValue("");
		}

		String str = (policyFloor.getType()==0?"固定底价":"折扣底价")+","
		+policyFloor.getFloorPrice()+","+policyFloor.getFloorPoint()+","
		+policyFloor.getUnder()+","
		+(policyFloor.isNeedSetCPA()==true?"CPA,":"")
		+(policyFloor.isNeedSetCPC()==true?"CPC,":"")
		+(policyFloor.isCompareCabin()==true?"比较舱位,":"")
		+(policyFloor.isStatus()==true?"有效":"无效");
		policyFloorLog.setNewValue(str);
		return policyFloorLog;
	}
	/**
	 * 保存底价修改日志
	 * @param policyPloorLog
	 */
	public PolicyFloorLog savePolicyFloorLog(PolicyFloorLog log){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = newConnection();
			/*id varchar(50) primary key,
		 	policy_floor_id varchar(50),
		 	log_data date,
		 	user_name varchar(50),
		 	opt_action int,
		 	old_value varchar(50),
		 	new_value varchar(50)*/
			if(log.getId()==null ||log.getId().trim().equals("")){
				stmt = conn.prepareStatement("insert into policy_floor_log values(?,?,?,?,?,?,?)");
				stmt.setString(1,System.currentTimeMillis()+""+random.nextInt());
				stmt.setString(2, log.getPolicyFloorId());
				stmt.setTimestamp(3, log.getDate());
				stmt.setString(4, log.getUsername());
				stmt.setInt(5, log.getAction());
				stmt.setString(6, log.getOldValue());
				stmt.setString(7, log.getNewValue());
			}else{
				stmt = conn.prepareStatement("UPDATE policy_floor_log " +
						"SET policy_floor_id=?, log_data=?,user_name=?,opt_action=?,old_value=?,new_value=?  where id=?");
				stmt.setString(1, log.getPolicyFloorId());
				stmt.setTimestamp(2, log.getDate());
				stmt.setString(3, log.getUsername());
				stmt.setInt(4, log.getAction());
				stmt.setString(5, log.getOldValue());
				stmt.setString(6, log.getNewValue());

			}

			stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}

		return log;
	}
	
	//根据底价编号查询设置日志

	public List<PolicyFloorLog> getPolicyFloorLogByPolicyFloorId(String policyFloorId){
		Connection conn = null;
		PreparedStatement stmt = null;
		PolicyFloorLog log = null;
		List<PolicyFloorLog> list = new ArrayList<PolicyFloorLog>();
		try{
		    conn = newConnection();
			stmt = conn.prepareStatement("select * from policy_floor_log where policy_floor_id=? ORDER BY id desc");
			stmt.setString(1, policyFloorId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				log = new PolicyFloorLog();
				log.setPolicyFloorId(rs.getString("policy_floor_id"));
				log.setId(rs.getString("id"));
				log.setDate(rs.getTimestamp("log_date"));
				log.setUsername(rs.getString("user_name"));
				log.setAction(rs.getInt("opt_action"));
				log.setOldValue(rs.getString("old_value"));
				log.setNewValue(rs.getString("new_value"));
				list.add(log);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
		
		return list;

	}
	
	

	
// 	id varchar(50) primary key,
// 	flightcode varchar(10),
// 	dpt varchar(10),
// 	arr varchar(10),
// 	price int
	public List<YPrice> getYPrice(String flightcode,String dpt, String arr ) throws Exception{
		List<YPrice> list= new ArrayList<YPrice>();

		Connection conn = null;
		Statement stmt = null;
		try{
		    conn = newConnection();
		    stmt = conn.createStatement();
			StringBuffer sbf = new StringBuffer();
			sbf.append("select * from y_price where 1=1 ");
			if(StringUtil.isNotEmptyOrNull(flightcode) ){
				sbf.append(" and flightcode='"+flightcode+"'");
			}
			if(StringUtil.isNotEmptyOrNull(dpt)){
				sbf.append(" and dpt='"+dpt+"'");
			}
			if(StringUtil.isNotEmptyOrNull(arr)){
				sbf.append(" and arr='"+arr+"'");
			}
			ResultSet rs = stmt.executeQuery(sbf.toString());
			System.out.println(sbf.toString());
			while(rs.next()){
				YPrice p = new YPrice();
				p.setId(rs.getString("id"));
				p.setDpt(rs.getString("dpt"));
				p.setArr(rs.getString("arr"));
				p.setFlightcode(rs.getString("flightcode"));
				p.setPrice(rs.getInt("price"));
				list.add(p);
			}
			
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
		
		return list;
	}

	public YPrice getYPriceSingle(String flightcode,String dpt, String arr ) throws Exception{
		List<YPrice> list = this.getYPrice(flightcode, dpt, arr);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	
	//判断Y舱价格是否存在相同记录
	public Boolean existYPrice(YPrice yPrice) throws Exception{
		YPrice tempYPrice = this.getYPriceSingle(yPrice.getFlightcode(), yPrice.getDpt(), yPrice.getArr());
	
		
		if(tempYPrice!=null ){
			if(yPrice.getId()==null){
				return true;
			}else{
				if(yPrice.getId().equals(tempYPrice.getId())){
					return false;
				}else{
					return true;
				}
				
			}
			
		}
		return false;
	}
	
	//保存Y舱价格
	public YPrice saveYPrice(YPrice yPrice){

		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = newConnection();
			if(yPrice.getId()==null ||yPrice.getId().trim().equals("")){
				stmt = conn.prepareStatement("insert into y_price values(?,?,?,?,?)");
				stmt.setString(1,System.currentTimeMillis()+""+random.nextInt());
				stmt.setString(2, yPrice.getFlightcode());
				stmt.setString(3, yPrice.getDpt());
				stmt.setString(4, yPrice.getArr());
				stmt.setInt(5, yPrice.getPrice());
			}else{
				stmt = conn.prepareStatement("UPDATE y_price " +
						"SET flightcode=?, dpt=?,arr=?,price=? where id=?");
				
				stmt.setString(1, yPrice.getFlightcode());
				stmt.setString(2, yPrice.getDpt());
				stmt.setString(3, yPrice.getArr());
				stmt.setInt(4, yPrice.getPrice());
				stmt.setString(5, yPrice.getId());
			}

			stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
		return yPrice;
	}
	
	public boolean deleteYPrice(String id){
		Connection conn = null;
		Statement  stmt = null;

        boolean flag = false;
        try{
            //获取连接
            conn = newConnection();
           
            //执行SQL
            String sql = "delete from y_price where id = '"+id+"'";
           System.out.println(sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            flag = true;
        }catch(Exception ex){
        	flag = false;
            ex.printStackTrace();
        }finally{
        	StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
        }
 
        return flag;
	}
	
	public void updateChangePriceLogPolicyId(String oldPolicyId,String newPolicyId){


		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = newConnection();
		
			stmt = conn.prepareStatement("UPDATE change_price_log " +
					"SET policy_id=?  where policy_id=?");
			
			stmt.setString(1, newPolicyId);
			stmt.setString(2, oldPolicyId);
			stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
		
	}
	
	public ChangePriceLog  insertPriceLog(ChangePriceLog log){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = newConnection();
			String id = System.currentTimeMillis()+""+random.nextInt();
			log.setId(id);
			stmt = conn.prepareStatement("insert into change_price_log values(?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1,log.getId());
			stmt.setString(2, log.getPolicy_id());
			stmt.setTimestamp(3, log.getLog_date());
			stmt.setString(4, log.getOld_returnpoint());
			stmt.setString(5, log.getNew_returnpoint());
			stmt.setString(6, log.getOld_returnprice());
			stmt.setString(7, log.getNew_returnprice());
			stmt.setString(8, log.getOld_cpc_returnpoint());
			stmt.setString(9, log.getNew_cpc_returnpoint());
			stmt.setString(10, log.getOld_cpc_returnprice());
			stmt.setString(11,log.getNew_cpc_returnprice());
			stmt.setString(12,log.getUser_name());
			
			stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}

		return log;
	}
	
	public List<ChangePriceLog> getPriceLogByPolicyId(String policyId) throws Exception{
		List<ChangePriceLog> list = new ArrayList<ChangePriceLog>();
		

		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			
		    conn = newConnection();
			stmt = conn.prepareStatement("select * from change_price_log where policy_id=?");
			stmt.setString(1, policyId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				ChangePriceLog log = new ChangePriceLog();
				log.setId(rs.getString(1));
				log.setPolicy_id(rs.getString(2));
				log.setLog_date(rs.getTimestamp(3));
				log.setOld_returnpoint(rs.getString(4));
				log.setNew_returnpoint(rs.getString(5));
				log.setOld_returnprice(rs.getString(6));
				log.setNew_returnprice(rs.getString(7));
				log.setOld_cpc_returnpoint(rs.getString(8));
				log.setNew_cpc_returnpoint(rs.getString(9));
				log.setOld_cpc_returnprice(rs.getString(10));
				log.setNew_cpc_returnprice(rs.getString(11));
				log.setUser_name(rs.getString(12));
				
				list.add(log);
		
			}
		}finally{
			StatementUtil.close(stmt);
			ConnectionUtil.close(conn);
		}
		
		return list;
	}
}
