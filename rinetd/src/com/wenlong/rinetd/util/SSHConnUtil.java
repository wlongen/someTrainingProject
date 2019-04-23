package com.wenlong.rinetd.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.wenlong.rinetd.entity.IPAndPort;
import com.wenlong.rinetd.entity.MyUserInfo;


/**
 * jsch实现连接工具类，提供创建连接，执行命令，断开连接等方法
 * @author twl
 *
 */
public class SSHConnUtil {
	//登陆主机地址
	private String ipAddress;  
	//登陆用户
    private String username;  
    //登陆用户密码
    private String password;  
   //默认端口号22
    public static final int DEFAULT_SSH_PORT = 22;  
    //连接
    private Session session;
    
    public SSHConnUtil(final String ipAddress, final String username, final String password) {  
        this.ipAddress = ipAddress;  
        this.username = username;  
        this.password = password;  
        session = this.getSession();
    }
    
    /**
     * 读取配置文件并保存
     */
	public Map<Integer, IPAndPort> readConfFile(String path) {
		Channel channel = null;
		BufferedReader reader = null;
		Map<Integer,IPAndPort> map = new HashMap<Integer,IPAndPort>();
		try {
			channel = session.openChannel("exec");
			String command = "cat "+path;
			((ChannelExec) channel).setCommand(command);

			channel.setInputStream(null);
			reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

			channel.connect();

			String line;
			int index = 1;
			while ((line = reader.readLine()) != null) {
				String[] ipAnPort = line.split(" ");
				if (ipAnPort != null && ipAnPort.length == 4) {
					IPAndPort ipAndPort = new IPAndPort(ipAnPort[0], ipAnPort[1], ipAnPort[2], ipAnPort[3]);
					map.put(index, ipAndPort);
				}
				index++;
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (channel != null) {
					channel.disconnect();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * shell执行
	 * @param command
	 * @return
	 */
	public void shell(final String ...command) {
		ChannelShell channelShell = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			channelShell = (ChannelShell) session.openChannel("shell");
			inputStream = channelShell.getInputStream();
			channelShell.setPty(true);
			channelShell.connect();
			// 写入该流的数据 都将发送到远程端
			outputStream = channelShell.getOutputStream();
			// 使用PrintWriter 就是为了使用println 这个方法
			// 好处就是不需要每次手动给字符加\n
			PrintWriter printWriter = new PrintWriter(outputStream);
			for (String string : command) {
				printWriter.println(string);
			}
			// 为了结束本次交互
			printWriter.println("exit");
			// 把缓冲区的数据强行输出
			printWriter.flush();

			byte[] tmp = new byte[1024];
			while (true) {

				while (inputStream.available() > 0) {
					int i = inputStream.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String s = new String(tmp, 0, i);
					if (s.indexOf("--More--") >= 0) {
						outputStream.write((" ").getBytes());
						outputStream.flush();
					}
					System.out.println(s);
				}
				if (channelShell.isClosed()) {
					System.out.println("exit-status:" + channelShell.getExitStatus());
					break;
				}
			}
		} catch (Exception e) {
			// TODO
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (channelShell != null) {
					channelShell.disconnect();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}
    
    /**
     * execute执行
     * @param command
     * @return
     */
	public void execute(final String command) {
		ChannelExec channel = null;
		try {
			
			// Channel的类型可以为如下类型：
			// shell - ChannelShell
			// exec - ChannelExec
			// direct-tcpip - ChannelDirectTCPIP
			// sftp - ChannelSftp
			// subsystem - ChannelSubsystem
			channel = (ChannelExec)session.openChannel("exec");
			channel.setCommand(command);
			channel.connect();
		} catch (Exception e) {
			// TODO
		}finally {
			if(channel != null) {
				channel.disconnect();
			}
		}
	}  

	/**
     *	 获取连接
     * @return
     */
	private Session getSession() {
		JSch jsch = new JSch();
		MyUserInfo userInfo = new MyUserInfo();
		Session session = null;
		try {
			// Create and connect session.
			session = jsch.getSession(username, ipAddress, DEFAULT_SSH_PORT);
			session.setPassword(password);
			session.setUserInfo(userInfo);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
		} catch (Exception e) {
			// TODO
		}
		return session;
	}
	
	/**
	   * 断开session连接
	 */
	public void closeSessionConn() {
		if(session != null) {
			session.disconnect();
		}
	}
}
