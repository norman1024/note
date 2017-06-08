package cn.tedu.note.web;

import java.awt.Color;import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.entity.User;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.Md5;

@Controller
@RequestMapping("/account")
public class AccountController{

	@Autowired
	private UserService userService;

	@RequestMapping("/login.do")
	@ResponseBody
	public JsonResult<User> login(String name, String password, String code, HttpServletRequest request,
			HttpServletResponse response) {

		String serverCode = (String) request.getSession().getAttribute("code");
		if (serverCode == null || !serverCode.equalsIgnoreCase(code)) {
			return new JsonResult<User>("验证码无效");
		}

		User user = userService.login(name, password);
		// 保存cookie token
		// 利用UserAgent 创建Token
		// User-Agent
		String userAgent = request.getHeader("User-Agent");
		long now = System.currentTimeMillis();
		String token = Md5.saltMd5(userAgent + now);
		Cookie cookie = new Cookie("token", now + "|" + token);
		cookie.setPath("/");
		response.addCookie(cookie);
		return new JsonResult<User>(user);

	}

	@RequestMapping("/regist.do")
	@ResponseBody
	public JsonResult<User> regist(String name, String password, String nick) {
		User user = userService.regist(name, password, nick);
		return new JsonResult<User>(user);
	}

	@RequestMapping(value = "/code.do", produces = "image/png")
	@ResponseBody
	public byte[] code(HttpServletRequest request) throws Exception {
		byte[] buf;

		BufferedImage img = new BufferedImage(80, 30, BufferedImage.TYPE_3BYTE_BGR);
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				img.setRGB(x, y, 0xEEEEEE);
			}
		}
		for (int i = 0; i < 1000; i++) {
			int x = (int) (Math.random() * 80);
			int y = (int) (Math.random() * 30);
			int rgb = (int) (Math.random() * 0xffffff);
			img.setRGB(x, y, rgb);
		}
		Graphics2D g = img.createGraphics();
		int rgb = (int) (Math.random() * 0xffffff);
		g.setColor(new Color(rgb));
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
		g.setFont(font);

		// 抗锯齿，平滑绘制
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		int x = (int) (Math.random() * 10);
		int y = (int) (Math.random() * 5);
		String code = randomCode();
		request.getSession().setAttribute("code", code);

		g.drawString(code, 3 + x, 28 - y);
		// 绘制5条乱线
		for (int i = 0; i < 5; i++) {
			int x1 = (int) (Math.random() * 80);
			int x2 = (int) (Math.random() * 80);
			int y1 = (int) (Math.random() * 30);
			int y2 = (int) (Math.random() * 30);
			rgb = (int) (Math.random() * 0xffffff);
			g.setColor(new Color(rgb));
			g.drawLine(x1, y1, x2, y2);
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		out.close();
		buf = out.toByteArray();
		return buf;

	}

	public String randomCode() {
		String chs = "4567ABCDEFHJKLXYabcdrhknp";
		char[] code = new char[4];
		for (int i = 0; i < code.length; i++) {
			int index = (int) (Math.random() * chs.length());
			code[i] = chs.charAt(index);
		}
		return new String(code);
	}

	@RequestMapping("/checkCode.do")
	@ResponseBody
	public JsonResult<Boolean> checkCode(String code, HttpServletRequest request) {
		String serverCode = (String) request.getSession().getAttribute("code");
		if (serverCode == null) {
			return new JsonResult<Boolean>("失败！");
		}
		if (serverCode.equalsIgnoreCase(code)) {
			return new JsonResult<Boolean>(true);
		}
		return new JsonResult<Boolean>("失败！");
	}
}
