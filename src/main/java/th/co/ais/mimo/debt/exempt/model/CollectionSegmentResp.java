package th.co.ais.mimo.debt.exempt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionSegmentResp {
	
	String errorMsg;
	List<CollectionSegmentDto> listRegion;

}
