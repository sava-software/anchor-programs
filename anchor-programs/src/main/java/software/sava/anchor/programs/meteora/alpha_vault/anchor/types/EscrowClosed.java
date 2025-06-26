package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record EscrowClosed(PublicKey vault,
                           PublicKey escrow,
                           PublicKey owner,
                           long vaultTotalEscrow) implements Borsh {

  public static final int BYTES = 104;

  public static EscrowClosed read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var escrow = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var vaultTotalEscrow = getInt64LE(_data, i);
    return new EscrowClosed(vault,
                            escrow,
                            owner,
                            vaultTotalEscrow);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vault.write(_data, i);
    i += 32;
    escrow.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, vaultTotalEscrow);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
