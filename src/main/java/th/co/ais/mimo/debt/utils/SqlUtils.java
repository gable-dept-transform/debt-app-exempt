package th.co.ais.mimo.debt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

public class SqlUtils {

    public static void close(EntityManager em) {
        if (em != null) {
            try {
                em.clear();
                em.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static <T> List<T> map(Class<T> type, List<Object[]> records){
    	List<T> result = new LinkedList<>();
    	for(Object[] record : records){
    		result.add(map(type, record));
    	}
    	return result;
    }
    
    private static <T> T map(Class<T> type, Object[] tuple){
    	   List<Class<?>> tupleTypes = new ArrayList<>();
    	   for(Object field : tuple){
    	      tupleTypes.add(field.getClass());
    	   }
    	   try {
    	      Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tuple.length]));
    	      return ctor.newInstance(tuple);
    	   } catch (Exception e) {
    	      throw new RuntimeException(e);
    	   }
    	}

    public static <T> List<T> getResultList(Query query, Class<T> type){
    	@SuppressWarnings("unchecked")
    	List<Object[]> records = query.getResultList();
    	return map(type, records);
    }
 
    @SuppressWarnings({"hiding", "deprecation" })
	public static List<?> convertToEntity(List<Tuple> inputList, Class<?> objClass) {
    	List<Object> arrayList = new ArrayList<>();
    	if(CollectionUtils.isNotEmpty(inputList)) { 
            inputList.stream().forEach(tuple -> {
                Map<String, Object> temp = new HashMap<>();
                tuple.getElements().
                        stream().
                        forEach(tupleElement ->
                                temp.put(tupleElement.getAlias().toLowerCase(),
                                        tuple.get(tupleElement.getAlias())));
                ObjectMapper map = new ObjectMapper();
                map.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true); 
                map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                
                try {
                    //converting to json
                    String mapToString = map.writeValueAsString(temp);
                    //converting json to entity
                    arrayList.add(map.readValue(mapToString, objClass));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }

            });
    	}
        return arrayList;
    }
    
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static List<Map<String, Object>> convertTuplesToMap(List<?> tuples) {
     
        List<Map<String, Object>> result = new ArrayList<>();

        tuples.forEach(object->{
            if(object instanceof Tuple ) {
                Map<String, Object> tempMap = new HashMap<>();
                final Tuple single = (Tuple) object;
       for (TupleElement<?> key : single.getElements()) {
                    tempMap.put(key.getAlias(), single.get(key));
                }
                result.add(tempMap);
            }else{
                throw new RuntimeException("Query should return instance of Tuple");
            }
        });

        return result;
    }

    
     public static <T> List<T> parseResult(List<?> list, Class<T> clz){
         List<T> result = new ArrayList<>();
         convertTuplesToMap(list).forEach(map->{
             result.add(objectMapper.convertValue(map, clz));
         });
         return result;
     }
}
