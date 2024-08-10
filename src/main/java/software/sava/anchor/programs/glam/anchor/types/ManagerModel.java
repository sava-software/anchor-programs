package software.sava.anchor.programs.glam.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record ManagerModel(String portfolioManagerName, byte[] _portfolioManagerName,
                           PublicKey pubkey,
                           ManagerKind kind) implements Borsh {


  public static ManagerModel createRecord(final String portfolioManagerName,
                                          final PublicKey pubkey,
                                          final ManagerKind kind) {
    return new ManagerModel(portfolioManagerName, Borsh.getBytes(portfolioManagerName), pubkey, kind);
  }

  public static ManagerModel read(final byte[] _data, final int offset) {
    int i = offset;
    final var portfolioManagerName = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (portfolioManagerName != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var pubkey = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (pubkey != null) {
      i += 32;
    }
    final var kind = _data[i++] == 0 ? null : ManagerKind.read(_data, i);
    return new ManagerModel(portfolioManagerName, Borsh.getBytes(portfolioManagerName), pubkey, kind);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(_portfolioManagerName, _data, i);
    i += Borsh.writeOptional(pubkey, _data, i);
    i += Borsh.writeOptional(kind, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenOptional(_portfolioManagerName) + Borsh.lenOptional(pubkey, 32) + Borsh.lenOptional(kind);
  }
}