#ifndef __POLY1305__
#define __POLY1305__

void poly1305(
		unsigned char *out,
		unsigned char *r,
		unsigned char *s,
		unsigned char *m,
		unsigned int l
		) {
	unsigned int j;
	Pword rbar(r);
	Pword h(0u);
	Pword p("\x03\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xfb");
	
	while (l > 0) {
		Pword c(0u);
		for (j = 0; (j < 16) && (j < l); ++j) {
			c.add(j, m[j]);
		}
		c.add(j, 1u);
		m += j; l -= j;
		h = ((h + c) * rbar) % p;
	}
	for (j = 0; j < 16; ++j)
		h.add(j, s[j]);

	for (j = 0; j < 16; ++j) {
		Pword c = h % 256;
		h >>= 8;
		out[j] = c.slot(0);
	}
}

#endif
