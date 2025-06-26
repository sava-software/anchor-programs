package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ProrataVaultParametersUpdated(PublicKey vault,
                                            long maxBuyingCap,
                                            long startVestingPoint,
                                            long endVestingPoint) implements Borsh {

  public static final int BYTES = 56;

  public static ProrataVaultParametersUpdated read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var maxBuyingCap = getInt64LE(_data, i);
    i += 8;
    final var startVestingPoint = getInt64LE(_data, i);
    i += 8;
    final var endVestingPoint = getInt64LE(_data, i);
    return new ProrataVaultParametersUpdated(vault,
                                             maxBuyingCap,
                                             startVestingPoint,
                                             endVestingPoint);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, maxBuyingCap);
    i += 8;
    putInt64LE(_data, i, startVestingPoint);
    i += 8;
    putInt64LE(_data, i, endVestingPoint);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
