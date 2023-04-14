package directories.mapper;

import directories.model.Region;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * MyBatis mapper
 */
public interface RegionMapper {
    @Select("SELECT * FROM REGIONS WHERE id = #{id};")
    Region findById(@Param("id") Long id);

    @Select("SELECT * FROM REGIONS;")
    List<Region> findAllRegions();

    @Select("SELECT * FROM REGIONS WHERE name = #{name};")
    List<Region> findByName(@Param("name") String name);

    @Select("SELECT * FROM REGIONS WHERE shortName = #{shortName};")
    List<Region> findByShortName(@Param("shortName") String shortName);

    @Select("SELECT * FROM REGIONS WHERE name = #{name} AND shortName = #{shortName};")
    Region findByNameAndShortName(@Param("name") String name, @Param("shortName") String shortName);

    @Insert("INSERT INTO REGIONS(name, shortName)\n" +
            "VALUES (#{name}, #{shortName});")
    Integer addRegion(@Param("name") String name, @Param("shortName") String shortName);

    @Update("UPDATE REGIONS\n" +
            "SET name=#{name}, shortName=#{shortName}" +
            "WHERE id = #{id};")
    void updateRegion(@Param("id") Long id, @Param("name") String name, @Param("shortName") String shortName);

    @Update("UPDATE REGIONS\n" +
            "SET shortName=#{shortName}" +
            "WHERE id = #{id};")
    void updateShortNameRegion(@Param("id") Long id, @Param("shortName") String shortName);

    @Update("UPDATE REGIONS\n" +
            "SET name=#{name}" +
            "WHERE id = #{id};")
    void updateNameRegion(@Param("id") Long id, @Param("name") String name);

    @Update("UPDATE REGIONS\n" +
            "SET name=#{nameNew}" +
            "WHERE name = #{nameLast};")
    void updateNameRegionByName( @Param("nameLast") String nameLast, @Param("nameNew") String nameNew);

    @Update("UPDATE REGIONS\n" +
            "SET shortName=#{shortNameNew}" +
            "WHERE shortName = #{shortNameLast};")
    void updateShortNameRegionByShortName(@Param("shortNameLast") String shortNameLast, @Param("shortNameNew") String shortNameNew);

    @Delete("DELETE FROM REGIONS WHERE id = #{id};")
    void deleteRegionById(@Param("id") Long id);

    @Delete("DELETE FROM REGIONS WHERE name = #{name};")
    void deleteRegionByName(@Param("name") String name);

    @Delete("DELETE FROM REGIONS WHERE shortName = #{shortName};")
    void deleteRegionByShortName(@Param("shortName") String shortName);

    @Delete("DELETE FROM REGIONS WHERE name = #{name} AND shortName = #{shortName};")
    void deleteRegionByNameAndShortName(@Param("name") String name, @Param("shortName") String shortName);
}
