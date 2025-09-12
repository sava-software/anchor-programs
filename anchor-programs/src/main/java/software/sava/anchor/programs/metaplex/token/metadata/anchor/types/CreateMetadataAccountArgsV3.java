package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public record CreateMetadataAccountArgsV3(DataV2 data,
                                          boolean isMutable,
                                          CollectionDetails collectionDetails) implements Borsh {

  public static CreateMetadataAccountArgsV3 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var data = DataV2.read(_data, i);
    i += Borsh.len(data);
    final var isMutable = _data[i] == 1;
    ++i;
    final var collectionDetails = _data[i++] == 0 ? null : CollectionDetails.read(_data, i);
    return new CreateMetadataAccountArgsV3(data, isMutable, collectionDetails);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(data, _data, i);
    _data[i] = (byte) (isMutable ? 1 : 0);
    ++i;
    i += Borsh.writeOptional(collectionDetails, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(data) + 1 + (collectionDetails == null ? 1 : (1 + Borsh.len(collectionDetails)));
  }
}
