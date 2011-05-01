/*
peek-into-github

Developed by Rishabh Rao
rishabhsrao.wordpress.com
rishabhsrao@gmail.com
twitter.com/rishabhsrao

This file is a part of peek-into-github.

peek-into-github is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

peek-into-github is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with peek-into-github. If not, see <http://www.gnu.org/licenses/>.
*/
package risbee.pig.core.net;

/**
 * This interface must be implemented by the classes communicating with
 * Github.
 * @author Rishabh Rao
 */
public interface ICommunicator {
	/**
	 * This method refreshes the local data with fresh data from Github.
	 */
	public void refresh();
}
