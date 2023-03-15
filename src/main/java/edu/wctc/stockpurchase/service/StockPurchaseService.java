package edu.wctc.stockpurchase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import edu.wctc.stockpurchase.entity.StockPurchase;
import edu.wctc.stockpurchase.repo.StockPurchaseRepository;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockPurchaseService {
    private StockPurchaseRepository repo;
    private ObjectMapper objectMapper;

    @Autowired
    public StockPurchaseService(StockPurchaseRepository spr) {
        this.repo = spr;
    }

    public List<StockPurchase> getAllStocks(){
        List<StockPurchase> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        return list;
    }
    public StockPurchase getStock(int id) throws ResourceNotFoundException{
        return repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Stock","Id",id));
    }
    public StockPurchase save(StockPurchase stock){
        return repo.save(stock);
    }
    public StockPurchase patch(int id, JsonPatch patch) throws ResourceNotFoundException, JsonPatchException, JsonProcessingException{
        StockPurchase existingStock = getStock(id);
        JsonNode patched = patch.apply(objectMapper.convertValue(existingStock, JsonNode.class));
        StockPurchase patchedStock = objectMapper.treeToValue(patched, StockPurchase.class);
        repo.save(patchedStock);
        return patchedStock;
    }
    public StockPurchase update(StockPurchase stock) throws ResourceNotFoundException{
        if(repo.existsById(stock.getId())){
            return repo.save(stock);
        }
        else {
            throw new ResourceNotFoundException("Stock","Id", stock.getId());
        }
    }
    public void delete(int id) throws ResourceNotFoundException{
        if (repo.existsById(id)){
            repo.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("Stock","Id", id);
        }
    }
}
