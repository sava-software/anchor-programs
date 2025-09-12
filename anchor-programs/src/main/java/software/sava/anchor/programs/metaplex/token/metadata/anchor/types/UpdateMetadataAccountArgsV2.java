package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.lang.Boolean;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record UpdateMetadataAccountArgsV2(DataV2 data,
                                          PublicKey updateAuthority,
                                          Boolean primarySaleHappened,
                                          Boolean isMutable) implements Borsh {

  public static UpdateMetadataAccountArgsV2 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var data = _data[i++] == 0 ? null : DataV2.read(_data, i);
    if (data != null) {
      i += Borsh.len(data);
    }
    final var updateAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (updateAuthority != null) {
      i += 32;
    }
    final var primarySaleHappened = _data[i++] == 0 ? null : _data[i] == 1;
    if (primarySaleHappened != null) {
      ++i;
    }
    final var isMutable = _data[i++] == 0 ? null : _data[i] == 1;
    return new UpdateMetadataAccountArgsV2(data,
                                           updateAuthority,
                                           primarySaleHappened,
                                           isMutable);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(data, _data, i);
    i += Borsh.writeOptional(updateAuthority, _data, i);
    i += Borsh.writeOptional(primarySaleHappened, _data, i);
    i += Borsh.writeOptional(isMutable, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (data == null ? 1 : (1 + Borsh.len(data))) + (updateAuthority == null ? 1 : (1 + 32)) + (primarySaleHappened == null ? 1 : (1 + 1)) + (isMutable == null ? 1 : (1 + 1));
  }
}
