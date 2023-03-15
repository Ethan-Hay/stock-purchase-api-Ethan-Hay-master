package edu.wctc.stockpurchase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.service.StockPurchaseService;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/stockpurchases")
public class StockPurchaseController {

    private StockPurchaseService service;

    @Autowired
    public StockPurchaseController(StockPurchaseService sps) {
        this.service = sps;
    }

    @GetMapping
    public List<StockPurchase> getStocks(){
        return  service.getAllStocks();
    }
    @GetMapping("/{purchaseId}")
    public StockPurchase getStock(@PathVariable String purchaseId) {
        try {
            int id = Integer.parseInt(purchaseId);
            return service.getStock(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Student ID must be a number", e);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }
    @PostMapping
    public StockPurchase createStock(@RequestBody StockPurchase newStock){
        newStock.setId(0);
        return service.save(newStock);
    }
    @DeleteMapping("/{purchaseId}")
    public String deleteStock(@PathVariable String purchaseId) {
        try {
            int id = Integer.parseInt(purchaseId);
            service.delete(id);
            return "Purchase deleted: ID " + purchaseId;
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Purchase ID must be a number", e);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }

    @PatchMapping("/{purchaseId}")
    public StockPurchase patchStock(@PathVariable String purchaseId,
                                @RequestBody JsonPatch patch) {
        try {
            int id = Integer.parseInt(purchaseId);
            return service.patch(id, patch);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Purchase ID must be a number", e);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid patch format: " + e.getMessage(), e);
        }
    }

    @PutMapping
    public StockPurchase updateStock(@RequestBody StockPurchase stock) {
        try {
            return service.update(stock);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage(), e);
        }
    }
}
