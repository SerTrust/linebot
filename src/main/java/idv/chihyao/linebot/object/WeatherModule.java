package idv.chihyao.linebot.object;

import java.util.Date;
import java.util.List;

public class WeatherModule {
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

	public static class Records{
		private String datasetDescription;
		private List<Location> location;
		
		public static class Location{
			private String locationName;
			private List<WeatherElement> weatherElement;
			
			public static class WeatherElement{
				private String elementName;
				private List<Time> time;
				
				public static class Time{
					private String startTime;
					private String endTime;
					private Parameter parameter;
					
					public static class Parameter{
						private String parameterName;
						private String parameterValue;
						private String parameterUnit;
						
						public String getParameterName() {
							return parameterName;
						}
						public void setParameterName(String parameterName) {
							this.parameterName = parameterName;
						}
						public String getParameterValue() {
							return parameterValue;
						}
						public void setParameterValue(String parameterValue) {
							this.parameterValue = parameterValue;
						}
						public String getParameterUnit() {
							return parameterUnit;
						}
						public void setParameterUnit(String parameterUnit) {
							this.parameterUnit = parameterUnit;
						}	
						
					}

					public Parameter getParameter() {
						return parameter;
					}

					public void setParameter(Parameter parameter) {
						this.parameter = parameter;
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
