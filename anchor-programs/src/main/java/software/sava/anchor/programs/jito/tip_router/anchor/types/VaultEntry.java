package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultEntry(PublicKey vault,
                         PublicKey stMint,
                         long vaultIndex,
                         long slotRegistered,
                         byte[] reserved) implements Borsh {

  public static final int BYTES = 208;

  public static VaultEntry read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var stMint = readPubKey(_data, i);
    i += 32;
    final var vaultIndex = getInt64LE(_data, i);
    i += 8;
    final var slotRegistered = getInt64LE(_data, i);
    i += 8;
    final var reserved = new byte[128];
    Borsh.readArray(reserved, _data, i);
    return new VaultEntry(vault,
                          stMint,
                          vaultIndex,
                          slotRegistered,
                          reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vault.write(_data, i);
    i += 32;
    stMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, vaultIndex);
    i += 8;
    putInt64LE(_data, i, slotRegistered);
    i += 8;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
