package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Controls the bank's e-mode configuration, allowing certain collateral sources to be treated more
// favorably as collateral when used to borrow from this bank.
public record EmodeSettings(// This bank's NON-unique id that other banks will use to determine what emode rate to use when
                            // this bank is offered as collateral.
                            // 
                            // For example, all stablecoin banks might share the same emode_tag, and in their entries, each
                            // such stablecoin bank will recognize that collateral sources with this "stable" tag get
                            // preferential weights. When a new stablecoin is added that is considered riskier, it may get
                            // a new, less favorable emode tag, and eventually get upgraded to the same one as the other
                            // stables
                            // 
                            // * 0 is in an invalid tag and will do nothing.
                            int emodeTag,
                            byte[] pad0,
                            // Unix timestamp from the system clock when emode state was last updated
                            long timestamp,
                            // * EMODE_ON (1) - If set, at least one entry is configured. Never update this flag manually,
                            // it should always be equivalent to `EmodeConfig.has_entries`
                            // * 2, 4, 8, etc, Reserved for future use
                            long flags,
                            EmodeConfig emodeConfig) implements Borsh {

  public static final int BYTES = 424;
  public static final int PAD_0_LEN = 6;

  public static EmodeSettings read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var emodeTag = getInt16LE(_data, i);
    i += 2;
    final var pad0 = new byte[6];
    i += Borsh.readArray(pad0, _data, i);
    final var timestamp = getInt64LE(_data, i);
    i += 8;
    final var flags = getInt64LE(_data, i);
    i += 8;
    final var emodeConfig = EmodeConfig.read(_data, i);
    return new EmodeSettings(emodeTag,
                             pad0,
                             timestamp,
                             flags,
                             emodeConfig);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, emodeTag);
    i += 2;
    i += Borsh.writeArray(pad0, _data, i);
    putInt64LE(_data, i, timestamp);
    i += 8;
    putInt64LE(_data, i, flags);
    i += 8;
    i += Borsh.write(emodeConfig, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
