package seckill.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import seckill.dto.Exposer;
import seckill.dto.SeckillExecution;
import seckill.dto.SeckillResult;
import seckill.entity.Product;
import seckill.enums.SeckillStateEnum;
import seckill.exception.DataModifiedException;
import seckill.exception.RepeatSeckillException;
import seckill.exception.SeckillClosedException;
import seckill.service.SeckillService;

import java.util.Date;
import java.util.List;

/**
 * Created by taowang on 1/27/2017.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(path = "/list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<List<Product>> list() {
        SeckillResult<List<Product>> result;
        try {
            List<Product> productList = seckillService.getProductList();
            result = new SeckillResult<List<Product>>(true, productList);
        } catch (Exception e) {
            result = new SeckillResult<List<Product>>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(path = "/{productId}/detail", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Product> detail(@PathVariable("productId") Long productId) {
        SeckillResult<Product> result;
        try {
            Product product = seckillService.getProductById(productId);
            result = new SeckillResult<Product>(true, product);
        } catch (Exception e) {
            result = new SeckillResult<Product>(false, e.getMessage());
        }
        return result;
    }

    /**
     * @return current server time
     */
    @RequestMapping(path = "/time/now", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> currentTime() {
        Date currentTime = new Date();
        return new SeckillResult<Long>(true, currentTime.getTime());
    }

    @RequestMapping(path = "/{productId}/exposer", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposeSeckillUrl(@PathVariable("productId") Long productId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exposeSeckillUrl(productId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(path = "/{productId}/{md5}/execution", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> executeSeckill(@PathVariable("productId") Long productId, @PathVariable("md5") String md5, @CookieValue(value = "userPhone", required = false) Long userPhone) {

        if (userPhone == null) {
            return new SeckillResult<SeckillExecution>(false, "Not Signed in");
        }

        SeckillResult<SeckillExecution> result;

        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(productId, userPhone, md5);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (RepeatSeckillException rse) {
            SeckillExecution execution = new SeckillExecution(SeckillStateEnum.REPEATED_SECKILL);
            result = new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillClosedException sce) {
            SeckillExecution execution = new SeckillExecution(SeckillStateEnum.END);
            result = new SeckillResult<SeckillExecution>(true, execution);
        } catch (DataModifiedException dme) {
            SeckillExecution execution = new SeckillExecution(SeckillStateEnum.DATA_MODIFIED);
            result = new SeckillResult<SeckillExecution>(true, execution);
        } catch (Exception e) {
            SeckillExecution execution = new SeckillExecution(SeckillStateEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(false, execution, e.getMessage());
        }

        return result;
    }

}
