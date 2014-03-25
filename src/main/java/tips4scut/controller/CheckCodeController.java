package tips4scut.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = ("/ajax"))
public class CheckCodeController {


    char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @RequestMapping(value = "checkCode")
    public void getCode(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int width = 100;
        int height = 35;
        int codeCount = 4;
        int xx = 20;
        int fontHeight = 30;
        int codeY = 24;

        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        Random random = new Random();
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        Font font = new Font("Fixedly", Font.BOLD, fontHeight);
        gd.setFont(font);


        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        StringBuffer randomCode = new StringBuffer();
        int red;
        int green;
        int blue;

        for (int i = 0; i < codeCount; i++) {
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i) * xx, codeY);
            randomCode.append(code);
        }
        HttpSession session = req.getSession();
        System.out.print(randomCode);
        session.setAttribute("checkCode", randomCode.toString());

        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

        resp.setContentType("image/jpeg");

        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }

}