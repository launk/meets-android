package com.theagilemonkeys.meets.utils.soap;

public static class Object implements KvmSerializable {
        private static final java.util.List unserializableTypes = Arrays.asList(Float.class, float.class, Double.class, double.class);
        private transient java.util.List<Field> fields = null;

        private java.util.List<Field> getFields(){
            if (fields != null) return fields;

            fields = new ArrayList<Field>();

            for(Field field : getAllFields(getClass()) ){
                Key keyAnnotation = field.getAnnotation(Key.class);
                if ( keyAnnotation != null ) fields.add(field);
            }

            return fields;
        }


        private java.lang.Object getPropertyValue(Field field) throws IllegalAccessException {
            // This is fix the serialization problems Soap has with doubles and floats
            java.lang.Object val = field.get(this);
            if ( unserializableTypes.contains(field.getType()) ) {
                val = String.valueOf(val);
            }
            return val;
        }

        @Override
        public java.lang.Object getProperty(int i) {
            try{
                Field field = getFields().get(i);
                field.setAccessible(true);
                return getPropertyValue(field);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        @Override
        public int getPropertyCount() {
            return getFields().size();
        }

        @Override
        public void setProperty(int i, java.lang.Object o) {
            try{
                Field field = getFields().get(i);
                field.setAccessible(true);
                field.set(this, o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        @Override
        public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
            Field field = getFields().get(i);

            //Check if this field has a specific name
            PropertyName propertyName = field.getAnnotation(PropertyName.class);
            if (propertyName != null)
                propertyInfo.setName(propertyName.value());
            else
                propertyInfo.setName(field.getName());

            propertyInfo.setType(field.getType());
        }
    }