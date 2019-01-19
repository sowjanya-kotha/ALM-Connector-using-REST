package infrastructure;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "entities" })
@XmlRootElement(name = "Entities")
public class Entities {

	@XmlElement(name = "Entity", required = true)
	protected List<Entity> entities;
	
	@XmlAttribute(name = "TotalResults", required = false)
	protected String totalResults;
	
	public Entities(Entities entitiesToCreate) {
		totalResults = entitiesToCreate.getTotalResults();
		entities = new ArrayList<Entity>(entitiesToCreate.getEntities());
	}
	
	public Entities() {
		
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public String getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}

	

}
