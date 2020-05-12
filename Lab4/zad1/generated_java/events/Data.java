// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto_source/events.proto

package events;

/**
 * Protobuf type {@code events.Data}
 */
public  final class Data extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:events.Data)
    DataOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Data.newBuilder() to construct.
  private Data(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Data() {
    type_ = 0;
    hour_ = 0;
    minute_ = 0;
    second_ = 0;
    name_ = "";
    years_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Data(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 8: {
            int rawValue = input.readEnum();

            type_ = rawValue;
            break;
          }
          case 16: {

            hour_ = input.readInt32();
            break;
          }
          case 24: {

            minute_ = input.readInt32();
            break;
          }
          case 32: {

            second_ = input.readInt32();
            break;
          }
          case 42: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
            break;
          }
          case 50: {
            java.lang.String s = input.readStringRequireUtf8();
            if (!((mutable_bitField0_ & 0x00000020) == 0x00000020)) {
              years_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000020;
            }
            years_.add(s);
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000020) == 0x00000020)) {
        years_ = years_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return events.EventsProto.internal_static_events_Data_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return events.EventsProto.internal_static_events_Data_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            events.Data.class, events.Data.Builder.class);
  }

  private int bitField0_;
  public static final int TYPE_FIELD_NUMBER = 1;
  private int type_;
  /**
   * <code>.events.Type type = 1;</code>
   */
  public int getTypeValue() {
    return type_;
  }
  /**
   * <code>.events.Type type = 1;</code>
   */
  public events.Type getType() {
    events.Type result = events.Type.valueOf(type_);
    return result == null ? events.Type.UNRECOGNIZED : result;
  }

  public static final int HOUR_FIELD_NUMBER = 2;
  private int hour_;
  /**
   * <code>int32 hour = 2;</code>
   */
  public int getHour() {
    return hour_;
  }

  public static final int MINUTE_FIELD_NUMBER = 3;
  private int minute_;
  /**
   * <code>int32 minute = 3;</code>
   */
  public int getMinute() {
    return minute_;
  }

  public static final int SECOND_FIELD_NUMBER = 4;
  private int second_;
  /**
   * <code>int32 second = 4;</code>
   */
  public int getSecond() {
    return second_;
  }

  public static final int NAME_FIELD_NUMBER = 5;
  private volatile java.lang.Object name_;
  /**
   * <code>string name = 5;</code>
   */
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <code>string name = 5;</code>
   */
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int YEARS_FIELD_NUMBER = 6;
  private com.google.protobuf.LazyStringList years_;
  /**
   * <code>repeated string years = 6;</code>
   */
  public com.google.protobuf.ProtocolStringList
      getYearsList() {
    return years_;
  }
  /**
   * <code>repeated string years = 6;</code>
   */
  public int getYearsCount() {
    return years_.size();
  }
  /**
   * <code>repeated string years = 6;</code>
   */
  public java.lang.String getYears(int index) {
    return years_.get(index);
  }
  /**
   * <code>repeated string years = 6;</code>
   */
  public com.google.protobuf.ByteString
      getYearsBytes(int index) {
    return years_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (type_ != events.Type.TIME.getNumber()) {
      output.writeEnum(1, type_);
    }
    if (hour_ != 0) {
      output.writeInt32(2, hour_);
    }
    if (minute_ != 0) {
      output.writeInt32(3, minute_);
    }
    if (second_ != 0) {
      output.writeInt32(4, second_);
    }
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, name_);
    }
    for (int i = 0; i < years_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, years_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (type_ != events.Type.TIME.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, type_);
    }
    if (hour_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, hour_);
    }
    if (minute_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, minute_);
    }
    if (second_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, second_);
    }
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, name_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < years_.size(); i++) {
        dataSize += computeStringSizeNoTag(years_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getYearsList().size();
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof events.Data)) {
      return super.equals(obj);
    }
    events.Data other = (events.Data) obj;

    boolean result = true;
    result = result && type_ == other.type_;
    result = result && (getHour()
        == other.getHour());
    result = result && (getMinute()
        == other.getMinute());
    result = result && (getSecond()
        == other.getSecond());
    result = result && getName()
        .equals(other.getName());
    result = result && getYearsList()
        .equals(other.getYearsList());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + TYPE_FIELD_NUMBER;
    hash = (53 * hash) + type_;
    hash = (37 * hash) + HOUR_FIELD_NUMBER;
    hash = (53 * hash) + getHour();
    hash = (37 * hash) + MINUTE_FIELD_NUMBER;
    hash = (53 * hash) + getMinute();
    hash = (37 * hash) + SECOND_FIELD_NUMBER;
    hash = (53 * hash) + getSecond();
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    if (getYearsCount() > 0) {
      hash = (37 * hash) + YEARS_FIELD_NUMBER;
      hash = (53 * hash) + getYearsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static events.Data parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static events.Data parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static events.Data parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static events.Data parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static events.Data parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static events.Data parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static events.Data parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static events.Data parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static events.Data parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static events.Data parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static events.Data parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static events.Data parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(events.Data prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code events.Data}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:events.Data)
      events.DataOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return events.EventsProto.internal_static_events_Data_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return events.EventsProto.internal_static_events_Data_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              events.Data.class, events.Data.Builder.class);
    }

    // Construct using events.Data.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      type_ = 0;

      hour_ = 0;

      minute_ = 0;

      second_ = 0;

      name_ = "";

      years_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000020);
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return events.EventsProto.internal_static_events_Data_descriptor;
    }

    public events.Data getDefaultInstanceForType() {
      return events.Data.getDefaultInstance();
    }

    public events.Data build() {
      events.Data result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public events.Data buildPartial() {
      events.Data result = new events.Data(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.type_ = type_;
      result.hour_ = hour_;
      result.minute_ = minute_;
      result.second_ = second_;
      result.name_ = name_;
      if (((bitField0_ & 0x00000020) == 0x00000020)) {
        years_ = years_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000020);
      }
      result.years_ = years_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof events.Data) {
        return mergeFrom((events.Data)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(events.Data other) {
      if (other == events.Data.getDefaultInstance()) return this;
      if (other.type_ != 0) {
        setTypeValue(other.getTypeValue());
      }
      if (other.getHour() != 0) {
        setHour(other.getHour());
      }
      if (other.getMinute() != 0) {
        setMinute(other.getMinute());
      }
      if (other.getSecond() != 0) {
        setSecond(other.getSecond());
      }
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      if (!other.years_.isEmpty()) {
        if (years_.isEmpty()) {
          years_ = other.years_;
          bitField0_ = (bitField0_ & ~0x00000020);
        } else {
          ensureYearsIsMutable();
          years_.addAll(other.years_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      events.Data parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (events.Data) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private int type_ = 0;
    /**
     * <code>.events.Type type = 1;</code>
     */
    public int getTypeValue() {
      return type_;
    }
    /**
     * <code>.events.Type type = 1;</code>
     */
    public Builder setTypeValue(int value) {
      type_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.events.Type type = 1;</code>
     */
    public events.Type getType() {
      events.Type result = events.Type.valueOf(type_);
      return result == null ? events.Type.UNRECOGNIZED : result;
    }
    /**
     * <code>.events.Type type = 1;</code>
     */
    public Builder setType(events.Type value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      type_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.events.Type type = 1;</code>
     */
    public Builder clearType() {
      
      type_ = 0;
      onChanged();
      return this;
    }

    private int hour_ ;
    /**
     * <code>int32 hour = 2;</code>
     */
    public int getHour() {
      return hour_;
    }
    /**
     * <code>int32 hour = 2;</code>
     */
    public Builder setHour(int value) {
      
      hour_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 hour = 2;</code>
     */
    public Builder clearHour() {
      
      hour_ = 0;
      onChanged();
      return this;
    }

    private int minute_ ;
    /**
     * <code>int32 minute = 3;</code>
     */
    public int getMinute() {
      return minute_;
    }
    /**
     * <code>int32 minute = 3;</code>
     */
    public Builder setMinute(int value) {
      
      minute_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 minute = 3;</code>
     */
    public Builder clearMinute() {
      
      minute_ = 0;
      onChanged();
      return this;
    }

    private int second_ ;
    /**
     * <code>int32 second = 4;</code>
     */
    public int getSecond() {
      return second_;
    }
    /**
     * <code>int32 second = 4;</code>
     */
    public Builder setSecond(int value) {
      
      second_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 second = 4;</code>
     */
    public Builder clearSecond() {
      
      second_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object name_ = "";
    /**
     * <code>string name = 5;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string name = 5;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string name = 5;</code>
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string name = 5;</code>
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <code>string name = 5;</code>
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringList years_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureYearsIsMutable() {
      if (!((bitField0_ & 0x00000020) == 0x00000020)) {
        years_ = new com.google.protobuf.LazyStringArrayList(years_);
        bitField0_ |= 0x00000020;
       }
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getYearsList() {
      return years_.getUnmodifiableView();
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public int getYearsCount() {
      return years_.size();
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public java.lang.String getYears(int index) {
      return years_.get(index);
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public com.google.protobuf.ByteString
        getYearsBytes(int index) {
      return years_.getByteString(index);
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public Builder setYears(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureYearsIsMutable();
      years_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public Builder addYears(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureYearsIsMutable();
      years_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public Builder addAllYears(
        java.lang.Iterable<java.lang.String> values) {
      ensureYearsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, years_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public Builder clearYears() {
      years_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000020);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string years = 6;</code>
     */
    public Builder addYearsBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureYearsIsMutable();
      years_.add(value);
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:events.Data)
  }

  // @@protoc_insertion_point(class_scope:events.Data)
  private static final events.Data DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new events.Data();
  }

  public static events.Data getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Data>
      PARSER = new com.google.protobuf.AbstractParser<Data>() {
    public Data parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Data(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Data> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Data> getParserForType() {
    return PARSER;
  }

  public events.Data getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
