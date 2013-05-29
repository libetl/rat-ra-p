package org.toilelibre.libe.ratrap.server.build;

import java.io.IOException;
import java.util.Map;


import org.toilelibre.libe.ratrap.server.build.csv.CsvToBeanMaps;
import org.toilelibre.libe.ratrap.shared.DataMap;
import org.toilelibre.libe.ratrap.shared.gtfs.Agency;

public class BuildGTFSModel implements Builder {

	private static final String files = "agency,routes,stops,calendar_dates,calendar,trips,stop_times,transfers";

	private DataMap dm = null;

	@Override
	public void build(String dir) throws IOException {
		if (this.dm == null) {
			return;
		}
		//EntityManager em = EMF.get().createEntityManager();
		/*Number n = (Number) em.createQuery("SELECT COUNT(a) FROM Agency a").getSingleResult();
		if (n.intValue() > 0){
			return;
		}*/
		CsvToBeanMaps ctb = new CsvToBeanMaps(Agency.class.getPackage());
		Agency agency = null;
		for (String file : BuildGTFSModel.files.split(",")) {
			try {
				Map<Object, Object> result = ctb.toBeanMaps(dir, file + ".txt");
				if ("agency".equals(file) && result != null && result.size() == 1){
					agency = (Agency) result.values().toArray() [0];
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		this.dm.setAgency(agency);
		//em.close();
	}

	@Override
	public void setDataMap(DataMap dm1) {
		this.dm = dm1;
	}

}
