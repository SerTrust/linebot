package idv.chihyao.linebot.object;

import java.util.Date;
import java.util.List;

public class WeatherTownshipModule {
	private boolean success;
	private Result result;
	private Records records;

	public static class Result {
		private String resource_id;
		List<Field> fields;

		public String getResource_id() {
			return resource_id;
		}

		public void setResource_id(String resource_id) {
			this.resource_id = resource_id;
		}

		public List<Field> getFields() {
			return fields;
		}

		public void setFields(List<Field> fields) {
			this.fields = fields;
		}

		public static class Field {
			private String id;
			private String type;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}
		}
	}

	public static class Records {
		private List<Locations> locations;

		public static class Locations {
			private String datasetDescription;
			private String locationsName;
			private String dataid;
			private List<Location> location;

			public static class Location {
				private String locationName;
				private String geocode;
				private String lat;
				private String lon;
				private List<WeatherElement> weatherElement;

				public static class WeatherElement {
					private String elementName;
					private String description;
					private List<Time> time;

					public static class Time {
						private String dataTime;
						private String startTime;
						private String endTime;
						private List<ElementValue> elementValue;

						public static class ElementValue {
							private String value;
							private String measures;

							public String getValue() {
								return value;
							}

							public void setValue(String value) {
								this.value = value;
							}

							public String getMeasures() {
								return measures;
							}

							public void setMeasures(String measures) {
								this.measures = measures;
							}
						}

						public String getDataTime() {
							return dataTime;
						}

						public void setDataTime(String dataTime) {
							this.dataTime = dataTime;
						}

						public List<ElementValue> getElementValue() {
							return elementValue;
						}

						public void setElementValue(List<ElementValue> elementValue) {
							this.elementValue = elementValue;
						}

						public String getStartTime() {
							return startTime;
						}

						public void setStartTime(String startTime) {
							this.startTime = startTime;
						}

						public String getEndTime() {
							return endTime;
						}

						public void setEndTime(String endTime) {
							this.endTime = endTime;
						}

					}

					public String getDescription() {
						return description;
					}

					public void setDescription(String description) {
						this.description = description;
					}

					public String getElementName() {
						return elementName;
					}

					public void setElementName(String elementName) {
						this.elementName = elementName;
					}

					public List<Time> getTime() {
						return time;
					}

					public void setTime(List<Time> time) {
						this.time = time;
					}
				}

				public String getGeocode() {
					return geocode;
				}

				public void setGeocode(String geocode) {
					this.geocode = geocode;
				}

				public String getLat() {
					return lat;
				}

				public void setLat(String lat) {
					this.lat = lat;
				}

				public String getLon() {
					return lon;
				}

				public void setLon(String lon) {
					this.lon = lon;
				}

				public String getLocationName() {
					return locationName;
				}

				public void setLocationName(String locationName) {
					this.locationName = locationName;
				}

				public List<WeatherElement> getWeatherElement() {
					return weatherElement;
				}

				public void setWeatherElement(List<WeatherElement> weatherElement) {
					this.weatherElement = weatherElement;
				}

			}

			public String getLocationsName() {
				return locationsName;
			}

			public void setLocationsName(String locationsName) {
				this.locationsName = locationsName;
			}

			public String getDataid() {
				return dataid;
			}

			public void setDataid(String dataid) {
				this.dataid = dataid;
			}

			public String getDatasetDescription() {
				return datasetDescription;
			}

			public void setDatasetDescription(String datasetDescription) {
				this.datasetDescription = datasetDescription;
			}

			public List<Location> getLocation() {
				return location;
			}

			public void setLocation(List<Location> location) {
				this.location = location;
			}
		}

		public List<Locations> getLocations() {
			return locations;
		}

		public void setLocations(List<Locations> locations) {
			this.locations = locations;
		}

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Records getRecords() {
		return records;
	}

	public void setRecords(Records records) {
		this.records = records;
	}

}
