package org.launchcode.models.data;

import org.launchcode.models.Cheese;
import org.springframework.data.repository.CrudRepository;

public interface CheeseDao extends CrudRepository<Cheese, Integer> {
}
