package com.cars.backend.Repositories;

import com.cars.backend.Models.Dao.BrandsDao;
import com.cars.backend.Models.Dao.CarAdvertsDao;
import com.cars.backend.Models.Dao.ModelsDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface CarAdvertsRepository extends JpaRepository<CarAdvertsDao,Long> {
	@Query("SELECT c FROM CarAdvertsDao c WHERE c.engine = :engine")
	Page<CarAdvertsDao> findByEngine(@Param("engine") String engine, Pageable pageable);

	//the bellow query is the originial working query. commented in order to try to add filtration by date
//	@Query(value = "SELECT ca.* FROM car_adverts_dao ca " +
//			"JOIN models_dao md ON md.id = ca.model_id "+
//			"JOIN brands_dao bd ON md.brand_id = bd.id WHERE "+
//			"(:brand is null or brand_id = :brand) " +
//			"and (:model is null or model_id = :model) " +
//			"and (:engine is null or engine = :engine) " +
//			"and (:dateOfManufacturing is null or date_of_manufacturing = :dateOfManufacturing) " +
//			"and (:fromPrice is null or price > :fromPrice) "+
//			"and (:toPrice is null or price < :toPrice)",
//			nativeQuery = true)
//	Page<CarAdvertsDao> findByDynamicParams(@Param("model") Long model_id,
//											@Param("brand") Long brand_id,
//											@Param("fromPrice") Float fromPrice,
//											@Param("toPrice") Float toPrice,
//											@Param("engine") String engine,
//											@Param("dateOfManufacturing") Date dateOfManufacturing,
//											Pageable pageable);

	//Second query trying to add filtration by date
	@Query(value = "SELECT ca.* FROM car_adverts_dao ca " +
			"JOIN models_dao md ON md.id = ca.model_id "+
			"JOIN brands_dao bd ON md.brand_id = bd.id WHERE "+
			"(:brand is null or brand_id = :brand) " +
			"and (:model is null or model_id = :model) " +
			"and (:engine is null or engine = :engine) " +
			"and (:beforeDate is null or date_of_manufacturing >= :beforeDate) " +
			"and (:toDate is null or date_of_manufacturing <= :toDate) " +
			"and (:fromPrice is null or price > :fromPrice) "+
			"and (:toPrice is null or price < :toPrice)",
			nativeQuery = true)
	Page<CarAdvertsDao> findByDynamicParams(@Param("model") Long model_id,
											@Param("brand") Long brand_id,
											@Param("fromPrice") Float fromPrice,
											@Param("toPrice") Float toPrice,
											@Param("engine") String engine,
											@Param("beforeDate") Date before,
											@Param("toDate") Date toDate,
											Pageable pageable);
	@Query(value = "SELECT COUNT(*) FROM car_adverts_dao ca " +
			"JOIN models_dao md ON md.id = ca.model_id "+
			"JOIN brands_dao bd ON md.brand_id = bd.id WHERE "+
			"(:brand is null or brand_id = :brand) " +
			"and (:model is null or model_id = :model) " +
			"and (:engine is null or engine = :engine) " +
			"and (:dateOfManufacturing is null or date_of_manufacturing = :dateOfManufacturing) " +
			"and (:fromPrice is null or price > :fromPrice) "+
			"and (:toPrice is null or price < :toPrice)",
			nativeQuery = true)
	Long findCountWithParams(@Param("model") Long model_id,
											@Param("brand") Long brand_id,
											@Param("fromPrice") Float fromPrice,
											@Param("toPrice") Float toPrice,
											@Param("engine") String engine,
											@Param("dateOfManufacturing") Date dateOfManufacturing);

	public Optional<CarAdvertsDao> findById(Long id);
}

