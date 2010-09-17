/**
 * The (Su)bversion Re(po)sitory (S)earch (E)ngine (SupoSE for short).
 *
 * Copyright (c) 2007, 2008, 2009, 2010 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
 * Copyright (c) 2007, 2008, 2009, 2010 by Karl Heinz Marbaise
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 *
 * The License can viewed online under http://www.gnu.org/licenses/gpl.html
 * If you have any questions about the Software or about the license
 * just write an email to license@soebes.de
 */
package com.soebes.supose.config.filter;

import com.soebes.supose.config.filter.model.Filter;
import com.soebes.supose.config.filter.model.Repository;

/**
 * @author Karl Heinz Marbaise
 *
 */
public class Filtering {

	private Filter filter;

	public Filtering(Filter filter) {
		this.setFilter(filter);
	}
	
	public boolean hasRepository(String repoId) {
		boolean result = false;
		for (Repository repository : getFilter().getRepositories().getRepository()) {
			if (repository.getId().equals(repoId)) {
				result = true;
			}
		}
		return result;
	}

	public Repository getRepository(String repoId) {
		Repository result = null;
		for (Repository repository : getFilter().getRepositories().getRepository()) {
			if (repository.getId().equals(repoId)) {
				result = repository;
			}
		}
		return result;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Filter getFilter() {
		return filter;
	}
}